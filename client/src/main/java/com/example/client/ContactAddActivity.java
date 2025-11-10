package com.example.client;

import android.Manifest;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.client.entity.Contact;
import com.example.client.util.PermissionUtil;

import java.util.ArrayList;

public class ContactAddActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_name;
    private EditText et_phone;
    private EditText et_email;

    private String[] permissions = new String[]{
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.READ_CONTACTS
    };
    private static final int REQUEST_CODE_CONTACT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_add);
        // 申请权限
        PermissionUtil.checkPermissions(this, permissions, REQUEST_CODE_CONTACT);

        et_name = findViewById(R.id.et_name);
        et_phone = findViewById(R.id.et_phone);
        et_email = findViewById(R.id.et_email);

        findViewById(R.id.btn_add_contact).setOnClickListener(this);
        findViewById(R.id.btn_read_contact).setOnClickListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_CONTACT: {
                if (PermissionUtil.checkGrant(grantResults)) {
                    Log.d("gq", "权限申请成功");
                } else {
                    Toast.makeText(this, "通讯录申请权限失败", Toast.LENGTH_SHORT).show();
                    goToAppSetting();
                }
                break;
            }
        }
    }

    // 跳转到应用设置页面
    private void goToAppSetting() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", getPackageName(), null));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_contact: {
                if (!PermissionUtil.checkPermissions(this, permissions, REQUEST_CODE_CONTACT))return;
                // 创建一个联系人对象
                Contact contact = new Contact();
                contact.name = et_name.getText().toString();
                contact.phone = et_phone.getText().toString();
                contact.email = et_email.getText().toString();

                // 方式一,使用ContentResolver多次写入,每次一个字段
                //addContacts(getContentResolver(), contact);
                // 方式二,批处理方式
                // 每一次操作都是一个ContentProviderOperation,构建一个操作集合,然后一次性执行
                // 好处是,要么全部成功,要么全部失败,保证了事务的一致性
                addFullContacts(getContentResolver(), contact);
                break;
            }
            case R.id.btn_read_contact: {
                if (!PermissionUtil.checkPermissions(this, permissions, REQUEST_CODE_CONTACT))return;
                readPhoneContacts(getContentResolver());
                break;
            }
        }
    }

    // 读取手机通讯录
    private void readPhoneContacts(ContentResolver resolver) {
        // 先查询 raw_contacts 表, 再根据 raw_contact_id 查询 data 表
        Cursor rawCursor = resolver.query(ContactsContract.RawContacts.CONTENT_URI, new String[]{ContactsContract.RawContacts._ID}, null, null, null);
        while (rawCursor.moveToNext()) {
            int rawContactId = rawCursor.getInt(0);
            Uri uri = Uri.parse("content://com.android.contacts/contacts/" + rawContactId + "/data");
            Cursor dataCursor = resolver.query(uri, null, null, null, null);
            Contact contact = new Contact();
            while (dataCursor.moveToNext()) {
                String mimetype = dataCursor.getString(dataCursor.getColumnIndex(ContactsContract.Data.MIMETYPE));
                String data1 = dataCursor.getString(dataCursor.getColumnIndex(ContactsContract.Data.DATA1));
                switch (mimetype) {
                    case ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE: { // 姓名
                        contact.name = data1;
                        break;
                    }
                    case ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE: { // 电话
                        contact.phone = data1;
                        break;
                    }
                    case ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE: { // 邮箱
                        contact.email = data1;
                        break;
                    }
                }
            }
            dataCursor.close();
            if (contact.name != null) {
                Log.d("gq", contact.toString());
            }
        }
        rawCursor.close();
    }

    // 往手机通讯录一次性添加一个联系人信息(包括主记录、姓名、电话号码、电子邮箱)
    private void addFullContacts(ContentResolver contentResolver, Contact contact) {
        // 创建一个插入联系人主记录的内容操作器
        ContentProviderOperation op_main = ContentProviderOperation
                .newInsert(ContactsContract.RawContacts.CONTENT_URI)
                //java.lang.NullPointerException: Attempt to invoke virtual method 'java.util.Set android.content.ContentValues.keySet()' on a null object reference
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build();

        // 创建一个插入联系人姓名记录的内容操作器
        ContentProviderOperation op_name = ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.Data.DATA2, contact.name)
                .build();

        // 创建一个插入联系人电话记录的内容操作器
        ContentProviderOperation op_phone = ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.Data.DATA1, contact.phone)
                .withValue(ContactsContract.Data.DATA2, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                .build();

        // 创建一个插入联系人邮箱记录的内容操作器
        ContentProviderOperation op_email = ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.Data.DATA1, contact.email)
                .withValue(ContactsContract.Data.DATA2, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                .build();

        try {
            // 声明一个内容操作器的列表,并将上面四个操作器添加到该列表中
            ArrayList<ContentProviderOperation> ops = new ArrayList<>();
            // 将联系人信息添加到内容提供器操作集合中
            ops.add(op_main);
            ops.add(op_name);
            ops.add(op_phone);
            ops.add(op_email);
            // 执行内容提供器操作集合
            contentResolver.applyBatch(ContactsContract.AUTHORITY, ops);
            Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "添加失败", Toast.LENGTH_SHORT).show();
        }
    }

    // 往手机通讯录添加一个联系人信息(包括姓名、电话、邮箱)
    private void addContacts(ContentResolver resolver, Contact contact) {
        ContentValues values = new ContentValues();
        // 往 raw_contacts 表添加联系人记录,并获取添加后的联系人编号
        Uri uri = resolver.insert(ContactsContract.RawContacts.CONTENT_URI, values);
        long rawContactId = ContentUris.parseId(uri);

        ContentValues name = new ContentValues();
        // 关联联系人编号
        name.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
        // "姓名"的数据类型
        name.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        // 联系人的姓名
        name.put(ContactsContract.Data.DATA2, contact.name);
        // 往提供器添加联系人的姓名记录
        resolver.insert(ContactsContract.Data.CONTENT_URI,  name);

        ContentValues phone = new ContentValues();
        // 关联联系人编号
        phone.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
        // "电话"的数据类型
        phone.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        // 联系人的电话
        phone.put(ContactsContract.Data.DATA1, contact.phone);
        // 联系类型。1:表示家庭，2:表示工作
        phone.put(ContactsContract.Data.DATA2, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
        // 往提供器添加联系人的电话记录
        resolver.insert(ContactsContract.Data.CONTENT_URI,  phone);

        ContentValues email = new ContentValues();
        // 关联联系人编号
        email.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
        // "邮箱"的数据类型
        email.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE);
        // 联系人的邮箱
        email.put(ContactsContract.Data.DATA1, contact.email);
        // 联系类型。1:表示家庭，2:表示工作
        email.put(ContactsContract.Data.DATA2, ContactsContract.CommonDataKinds.Email.TYPE_WORK);
        // 往提供器添加联系人的电话记录
        resolver.insert(ContactsContract.Data.CONTENT_URI,  email);
    }
}
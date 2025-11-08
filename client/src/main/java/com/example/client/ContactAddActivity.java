package com.example.client;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.client.entity.Contact;

public class ContactAddActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_name;
    private EditText et_phone;
    private EditText et_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_add);

        et_name = findViewById(R.id.et_name);
        et_phone = findViewById(R.id.et_phone);
        et_email = findViewById(R.id.et_email);

        findViewById(R.id.btn_add_contact).setOnClickListener(this);
        findViewById(R.id.btn_read_contact).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_contact: {
                // 创建一个联系人对象
                Contact contact = new Contact();
                contact.name = et_name.getText().toString();
                contact.phone = et_phone.getText().toString();
                contact.email = et_email.getText().toString();

                // 方式一,使用ContentResolver多次写入,每次一个字段
                addContacts(getContentResolver(), contact);
                break;
            }
            case R.id.btn_read_contact: {
                break;
            }
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
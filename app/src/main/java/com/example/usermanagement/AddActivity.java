package com.example.usermanagement;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class AddActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText nameInput, ageInput, emailInput;
    ImageView avatar;
    Button submitAddUserBtn;
    ImageButton imageBtn;
    private byte[] avatarBytes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        toolbar = findViewById(R.id.my_app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);


        avatar = findViewById(R.id.avatar);
        nameInput = findViewById(R.id.name_input);
        ageInput = findViewById(R.id.age_input);
        emailInput = findViewById(R.id.email_input);
        imageBtn = findViewById(R.id.imageBtn);
        imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(AddActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });

        submitAddUserBtn = findViewById(R.id.btn_submit_add_user);
        submitAddUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper db = new DatabaseHelper(AddActivity.this);
                Boolean isValidate = validateInfo();
                if (isValidate){
                    UserModel user = new UserModel(-1, nameInput.getText().toString(), Integer.parseInt(ageInput.getText().toString()), emailInput.getText().toString(), avatarBytes);
                    boolean isSuccess = db.addUser(user);
                    if(isSuccess){
                        //set text
                        nameInput.setText("");
                        ageInput.setText("");
                        emailInput.setText("");
                        avatar.setImageResource(R.drawable.ic_person);
                        avatarBytes = null;
                    }
                }
            }
        });
    }

    private byte[] getAvatarData(InputStream inputStream) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                avatarBytes = getAvatarData(inputStream);
                avatar.setImageURI(imageUri);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            // Handle errors from ImagePicker
            Toast.makeText(this, ImagePicker.Companion.getError(data), Toast.LENGTH_SHORT).show();
        }
    }


    private Boolean validateInfo(){
        if (TextUtils.isEmpty(nameInput.getText().toString())) {
            nameInput.setError("Please enter name");
            return false;
        }
        if (TextUtils.isEmpty(ageInput.getText().toString())) {
            ageInput.setError("Please enter age");
            return false;
        } else {
            int age = Integer.parseInt(ageInput.getText().toString());
            if (age <= 0) {
                ageInput.setError("Please enter a valid age (greater than 0)");
                return false;
            }
        }

        if (TextUtils.isEmpty(emailInput.getText().toString())) {
            emailInput.setError("Please enter email");
            return false;
        }else {
            String email = emailInput.getText().toString().trim();
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailInput.setError("Please enter a valid email address");
                return false;
            }
        }


        return true;
    }
}
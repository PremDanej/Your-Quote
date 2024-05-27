package com.merp.your.custom.quote.gen.ui;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.merp.your.custom.quote.gen.R;
import com.merp.your.custom.quote.gen.helper.DatabaseHelper;
import com.merp.your.custom.quote.gen.model.User;

import java.io.ByteArrayOutputStream;

public class HomeActivity extends AppCompatActivity {

    private EditText edtName, edtUserName;
    private ImageView imgUser;
    private Button btnSubmit;
    private PickVisualMediaRequest request;
    private MaterialCardView cardUser;
    private boolean isImageSelected = false;
    private boolean isFieldValidate = false;
    private DatabaseHelper helper;

    ActivityResultLauncher<PickVisualMediaRequest> pickImage = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
        if (uri != null) {
            Glide.with(getApplicationContext()).load(uri).centerCrop().into(imgUser);
            isImageSelected = true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        edtName = findViewById(R.id.edtName);
        edtUserName = findViewById(R.id.edtUserName);
        cardUser = findViewById(R.id.cardUser);
        imgUser = findViewById(R.id.imgUser);
        btnSubmit = findViewById(R.id.btnSubmit);
        request = new PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                .build();
        helper = new DatabaseHelper(this);

        edtUserName.setOnEditorActionListener(new DoneEditorActionListener());

        cardUser.setOnClickListener(view -> pickImage.launch(request));

        btnSubmit.setOnClickListener(view ->{
            isFieldValidate = checkAllFieldValidate();
            if(isFieldValidate){
                User user = new User();
                user.setName(edtName.getText().toString());
                user.setUsername(edtUserName.getText().toString());
                user.setEmail(edtUserName.getText().toString());
                user.setImage(getImageByte(imgUser));
                user.setIsVerified(0);
                helper.insertUser(user);
            }
        });
    }

    private byte[] getImageByte(ImageView imageView) {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,stream);
        return stream.toByteArray();
    }

    private boolean checkAllFieldValidate() {
        if(edtName.length() == 0){
            edtName.requestFocus();
            edtName.setError("This field is Required.");
            return  false;
        }
        if(edtUserName.length() == 0){
            edtUserName.requestFocus();
            edtUserName.setError("This field is Required.");
            return  false;
        }
        if(!isImageSelected){
            Toast.makeText(this, "Select Image", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    static class DoneEditorActionListener implements TextView.OnEditorActionListener {

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                v.clearFocus();
                return true;
            }
            return false;
        }
    }


    private Bitmap getImageBitmap(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

}
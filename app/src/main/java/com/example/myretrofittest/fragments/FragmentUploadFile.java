package com.example.myretrofittest.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myretrofittest.Interfaces.UserClient;
import com.example.myretrofittest.R;
import com.example.myretrofittest.Utils.PublicValue;

import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentUploadFile extends Fragment implements View.OnClickListener {

    private int REQUEST_GET_SINGLE_FILE = 258;

    private EditText editTextDescription;

    private ImageView imageViewPhoto;

    public FragmentUploadFile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upload_file, container, false);

        view.findViewById(R.id.button_upload_file).setOnClickListener(this);
        view.findViewById(R.id.button_choose_file).setOnClickListener(this);

        imageViewPhoto = view.findViewById(R.id.imageView_photo);
        editTextDescription = view.findViewById(R.id.editText_description);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_choose_file:
                openFilePicker();
                break;
            case R.id.button_upload_file:

                break;
        }
    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_GET_SINGLE_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == RESULT_OK) {
                if (requestCode == REQUEST_GET_SINGLE_FILE) {

                    if (data == null || data.getData() == null)
                        return;

                    Uri selectedImageUri = data.getData();

                    // Set the image in ImageView
                    imageViewPhoto.setImageURI(selectedImageUri);
                }
            }
        } catch (Exception e) {
            Log.e("FileSelectorActivity", "File select error", e);
        }
    }

    private void uploadFile(Uri fileUri) {
        String description = editTextDescription.getText().toString();

        Retrofit.Builder builder = new Retrofit
                .Builder()
                .baseUrl(PublicValue.BASE_URL_UPLOAD)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        UserClient client = retrofit.create(UserClient.class);

    }
}

package com.example.quizapplication;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import com.example.quizapplication.Adapter.CategoryAdapter;
import com.example.quizapplication.Models.CategoryModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;

import com.example.quizapplication.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    FirebaseDatabase database;
    FirebaseStorage storage;
    CircleImageView CategoryImage;
    EditText InputCategoryName;
    Button uploadCategory;
    View fetchImage;
    Dialog dialog;
    Uri imageUri;
    int i=0;
    ArrayList<CategoryModel>list;
    CategoryAdapter adapter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        database= FirebaseDatabase.getInstance();
        storage= FirebaseStorage.getInstance();
        list= new ArrayList<>();

        dialog=new Dialog(this);
        dialog.setContentView(R.layout.item_add_category_dialog);

        if(dialog.getWindow() != null)
        {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(true);
        }
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Uploading");
        progressDialog.setMessage("please wait");
        uploadCategory = dialog.findViewById(R.id.btnupload);
        InputCategoryName = dialog.findViewById(R.id.InputCategoryName);
        CategoryImage=dialog.findViewById(R.id.CategoryImage);
        fetchImage=dialog.findViewById(R.id.fetchImage);

        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        binding.recyCategory.setLayoutManager(layoutManager);

        adapter =new CategoryAdapter(this,list);
        binding.recyCategory.setAdapter(adapter);
        database.getReference().child("categories").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    list.clear();
                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                        list.add(new CategoryModel(
                                dataSnapshot.child("categoryName").getValue().toString(),
                                dataSnapshot.child("categoryImage").getValue().toString(),
                                dataSnapshot.getKey(),
                                Integer.parseInt(dataSnapshot.child("setNum").getValue().toString())
                        ));
                    }
                    adapter.notifyDataSetChanged();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "category not exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this,error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        binding.addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        fetchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,1);

            }
        });
        uploadCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name= InputCategoryName.getText().toString();
                if(imageUri !=null)
                {
                    Toast.makeText(MainActivity.this, "please upload category image", Toast.LENGTH_SHORT).show();
                }
                else if(name.isEmpty())
                {
                    InputCategoryName.setError("Enter Category Name");
                }
                else {
                    progressDialog.show();
                    uploadData();
                }
            }
        });

    }

    private void uploadData() {

        final StorageReference reference=storage.getReference().child("category").child(new Date().getTime()+"");
        reference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        CategoryModel categoryModel= new CategoryModel();
                        categoryModel.setCategoryName(InputCategoryName.getText().toString());
                        categoryModel.setSetNum(0);
                        categoryModel.setCategoryImage(uri.toString());

                        database.getReference().child("categories").child("category"+i++)
                                .setValue(categoryModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                        Toast.makeText(MainActivity.this, "data uploaded", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                        Toast.makeText(MainActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }
                                });

                    }
                });
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1)
        {
            if(data!=null)
            {
                imageUri =data.getData();
                CategoryImage.setImageURI(imageUri);
            }
        }
    }
}
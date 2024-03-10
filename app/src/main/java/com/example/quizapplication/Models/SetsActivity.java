package com.code.quizappadmin;

import ...

public class SetsActivity extends AppCompatActivity{

    ActivitySetsBinding binding;

    int a=1;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding=ActivitySetsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        database=FirebaseDatabase.getInstance();
        key= getIntent().getStringExtra("key");

        adapter= new GrideAdapter(getIntent().getIntExtra("sets",0)
            getIntent().getStringExtra("Category"),key,new GrideAdapter.GridListener(){

                @Override
                public void addSets(){
                   

                    database.getReference().child("categories").child(key)
                     .child("setNum").setValue(getIntent().getIntExtra("sets",0)+a++).addOnCompleteListener(addOnCompleteListener<Void>(){

                        @Override
                        public void OnComplete(@NonNull Task<Void> task){
                            

                            if(task.isSuccessful()){
                                adapter.sets++;
                                adapter.nutifyDataSetChanged();  
                                
                            }
                            else{

                                Toast.makeText(SetsActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }

                            
                        }
                     })
                }
            });



             binding.gridView.setAdapter(adapter);

    }
}
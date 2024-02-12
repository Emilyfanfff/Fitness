package com.example.fitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.fitnessapp.entity.SportData;
import com.example.fitnessapp.entity.User;
import com.example.fitnessapp.viewmodel.SportViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SignUp extends AppCompatActivity {
    private FirebaseAuth auth;
    private String birthday = "";
    private String gender = "";
    private SportViewModel sportViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        auth = FirebaseAuth.getInstance();
        Button sign_up_btn=findViewById(R.id.sign_up_btn);
        TextView have_already= findViewById(R.id.have_already);
        EditText signup_name=findViewById(R.id.signup_name);
        EditText signup_email=findViewById(R.id.signup_email);
        EditText signup_phone=findViewById(R.id.signup_phone);
        EditText signup_password=findViewById(R.id.signup_password);
        EditText signup_address=findViewById(R.id.signup_address);
        EditText signup_favourite_sport=findViewById(R.id.signup_favourite_sport);

        Button birthdayButton = findViewById(R.id.birthday_button);
        Button clearButton = findViewById(R.id.clear_btn);

        RadioGroup genderRadioGroup = findViewById(R.id.genderRadioGroup);

        sportViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())
                .create(SportViewModel.class);


        genderRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.maleRadioButton) {
                    gender = "male";
                } else if (checkedId == R.id.femaleRadioButton) {
                    gender = "female";
                }
            }
        });

        birthdayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
                builder.setTitleText("Select Birthday");

                MaterialDatePicker<Long> materialDatePicker = builder.build();

                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selectedDate) {
                        // 处理用户选择的日期
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        String newBirthday = format.format(new Date(selectedDate));
                        birthday = newBirthday;
                    }
                });

                materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
            }
        });

        have_already.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this,
                        MainActivity.class);
                startActivity(intent);
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup_name.setText("");
                signup_email.setText("");
                signup_password.setText("");
                signup_name.setText("");
                signup_phone.setText("");
                signup_address.setText("");
                signup_favourite_sport.setText("");
                genderRadioGroup.clearCheck();
                gender = "";
                birthday = "";
            }
        });

        CheckBox checkBox = findViewById(R.id.checkbox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    signup_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    signup_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        sign_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_txt = signup_email.getText().toString();
                String password_txt = signup_password.getText().toString();
                String name_txt = signup_name.getText().toString();
                String phone_txt = signup_phone.getText().toString();
                String address_txt = signup_address.getText().toString();
                String favourite_sport_txt = signup_favourite_sport.getText().toString();
                if (TextUtils.isEmpty(email_txt) ||
                        TextUtils.isEmpty(password_txt)) {
                    String msg = "Empty Email or Password";
                    toastMsg(msg);
                } else if (password_txt.length() < 6) {
                    String msg = "Password is too short";
                    toastMsg(msg);
                } else if(TextUtils.isEmpty(name_txt)){
                    String msg = "Empty Name";
                    toastMsg(msg);
                } else if(TextUtils.isEmpty(phone_txt)){
                    String msg = "Empty phone";
                    toastMsg(msg);
                } else if(TextUtils.isEmpty(address_txt)){
                    String msg = "Empty address";
                    toastMsg(msg);
                } else if(TextUtils.isEmpty(favourite_sport_txt)){
                    String msg = "Empty sport";
                    toastMsg(msg);
                }else if(TextUtils.isEmpty(gender)){
                    String msg = "Empty gender";
                    toastMsg(msg);
                }else if(TextUtils.isEmpty(birthday)){
                    String msg = "Empty birthday";
                    toastMsg(msg);
                }else if(!email_txt.matches("^\\w+@\\w+\\.[a-zA-Z]+$")){
                    String msg = "Wrong email format";
                    toastMsg(msg);
                }else{
                    registerUser(email_txt, password_txt,name_txt,phone_txt,address_txt,favourite_sport_txt,gender,birthday);
                }
            }
        });

    }

    private void registerUser(String email_txt, String password_txt,String name_txt,String phone_txt,String address_txt,String favourite_sport_txt,String gender,String birthday) {
        // To create username and password
        auth.createUserWithEmailAndPassword(email_txt,password_txt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    String msg = "Registration Successful";
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String uid = user.getUid();
                    FirebaseDatabase database = FirebaseDatabase.getInstance("https://profound-ripsaw-384110-default-rtdb.asia-southeast1.firebasedatabase.app");
                    DatabaseReference myRef = database.getReference();
                    User newUser = new User(uid,name_txt,email_txt,phone_txt,address_txt,favourite_sport_txt,gender,birthday);

                    //create new data entity for this customer to the room
                    //create date arraylist
                    ArrayList date_arr = new ArrayList<String>();
                    Calendar c = Calendar.getInstance();
                    int year = c.get(Calendar.YEAR);
                    int month = c.get(Calendar.MONTH) + 1;
                    int day = c.get(Calendar.DATE);
                    String date = Integer.toString(year) + " " + Integer.toString(month) + " " + Integer.toString(day);
                    date_arr.add(date);

                    //create sport arraylist and initial them
                    ArrayList dailyMinute = new ArrayList<Double>();
                    dailyMinute.add(0);
                    ArrayList yoga = new ArrayList<Double>();
                    yoga.add(0);
                    ArrayList swimming = new ArrayList<Double>();
                    swimming.add(0);
                    ArrayList jogging = new ArrayList<Double>();
                    jogging.add(0);
                    ArrayList boxing = new ArrayList<Double>();
                    boxing.add(0);
                    ArrayList rope_skipping = new ArrayList<Double>();
                    rope_skipping.add(0);
                    Log.i("uid",uid);

                    SportData newData = new SportData(uid, name_txt, date_arr, dailyMinute, yoga, swimming, jogging, boxing, rope_skipping);
                    sportViewModel.insert(newData);

                    myRef.child("users").child(uid).setValue(newUser);
                    startActivity(new Intent(SignUp.this,
                            MainActivity.class));
                    toastMsg(msg);
                }else {
                    String msg = "Registration Unsuccessful";
                    toastMsg(msg);
                }
            }
        });

    }

    public void toastMsg(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}

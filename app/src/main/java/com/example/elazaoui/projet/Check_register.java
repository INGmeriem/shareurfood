package com.example.elazaoui.projet;

/**
 * Created by aider-pc on 10/03/2016.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Check_register extends Activity {
    private final String EXTRA_NAME = "Name";
    private final String EXTRA_AGE = "Age";
    private final String EXTRA_LOGIN = "login";
    private final String EXTRA_PASSWORD = "password";
    private final String EXTRA_ADRESSE = "adresse";
    private final String EXTRA_ARRONDISSEMENT = "arrondissement";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< HEAD
        setContentView(R.layout.activity_register);
=======
        //setContentView(R.layout.activity_register);
>>>>>>> duy_version_test
    }
    public void doConnect(View view) {
        EditText txtName = (EditText) findViewById(R.id.etName);
        String strName = txtName.getText().toString();

        EditText txtUserAge = (EditText) findViewById(R.id.etAge);
        String strUserAge = txtUserAge.getText().toString();

        EditText txtUserName = (EditText) findViewById(R.id.etUsername);
        String strUserName = txtUserName.getText().toString();

        EditText txtUserPasswd = (EditText) findViewById(R.id.etPassword);
        String strUserPasswd = txtUserPasswd.getText().toString();

<<<<<<< HEAD
        EditText txtUserAdresse = (EditText) findViewById(R.id.etAdresse);
        String strUserAdresse = txtUserAdresse.getText().toString();

        EditText txtUserArrondissement = (EditText) findViewById(R.id.etArrondissement);
=======
        EditText txtUserAdresse = (EditText) findViewById(R.id.etAddress);
        String strUserAdresse = txtUserAdresse.getText().toString();

        EditText txtUserArrondissement = (EditText) findViewById(R.id.etPostalCode);
>>>>>>> duy_version_test
        String strUserArrondissement = txtUserArrondissement.getText().toString();


        if (TextUtils.isEmpty(strName)) {
            txtName.setError("Veuillez saisir votre nom");
            return;
        }
        if (TextUtils.isEmpty(strUserAge)) {
            txtUserAge.setError("Veuillez saisir votre age");
            return;
        }
        if (TextUtils.isEmpty(strUserName)) {
            txtUserName.setError("Veuillez saisir votre login");
            return;
        }
        if (TextUtils.isEmpty(strUserPasswd)) {
            txtUserPasswd.setError("Veuillez saisir votre mot de passe");
            return;
        }
        if (TextUtils.isEmpty(strUserAdresse)) {
            txtUserAdresse.setError("Veuillez saisir votre adresse");
            return;
        }
        if (TextUtils.isEmpty(strUserArrondissement)) {
            txtUserArrondissement.setError("Veuillez saisir votre arrondissement");
            return;
        }
        Toast.makeText(this, "doConnect", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, Register.class);
        intent.putExtra(EXTRA_NAME, strName);
        intent.putExtra(EXTRA_AGE, strUserAge);
        intent.putExtra(EXTRA_LOGIN, strUserName);
        intent.putExtra(EXTRA_PASSWORD, strUserPasswd);
        intent.putExtra(EXTRA_ADRESSE, strUserAdresse);
        intent.putExtra(EXTRA_ARRONDISSEMENT, strUserArrondissement);
        startActivity(intent);
    }
    public void doReset(View view) {
        Toast.makeText(this, "doReset", Toast.LENGTH_SHORT).show();
        EditText editText1 = (EditText) findViewById(R.id.etUsername);
        editText1.setText("");
        ((EditText) findViewById(R.id.etPassword)).setText("");
        ((EditText) findViewById(R.id.etName)).setText("");
        ((EditText) findViewById(R.id.etAge)).setText("");
<<<<<<< HEAD
        ((EditText) findViewById(R.id.etAdresse)).setText("");
        ((EditText) findViewById(R.id.etArrondissement)).setText("");
=======
        ((EditText) findViewById(R.id.etAddress)).setText("");
        ((EditText) findViewById(R.id.etPostalCode)).setText("");
>>>>>>> duy_version_test
    }
}
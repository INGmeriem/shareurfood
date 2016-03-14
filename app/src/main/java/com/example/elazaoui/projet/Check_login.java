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
public class Check_login extends Activity {
    private final String EXTRA_LOGIN = "login";
    private final String EXTRA_PASSWORD = "password";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_login);
    }
    public void doConnect(View view) {
        EditText txtUserName = (EditText) findViewById(R.id.etUsername);
        String strUserName = txtUserName.getText().toString();
        EditText txtUserPasswd = (EditText) findViewById(R.id.etPassword);
        String strUserPasswd = txtUserPasswd.getText().toString();
        if (TextUtils.isEmpty(strUserName)) {
            txtUserName.setError("Veuillez saisir votre login");
            return;
        }
        if (TextUtils.isEmpty(strUserPasswd)) {
            txtUserName.setError("Veuillez saisir votre mot de passe");
            return;
        }
        Toast.makeText(this, "doConnect", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, Login.class);
        intent.putExtra(EXTRA_LOGIN, strUserName);
        intent.putExtra(EXTRA_PASSWORD, strUserPasswd);
        startActivity(intent);
    }
    public void doReset(View view) {
        Toast.makeText(this, "doReset", Toast.LENGTH_SHORT).show();
        EditText editText1 = (EditText) findViewById(R.id.etUsername);
        editText1.setText("");
        ((EditText) findViewById(R.id.etPassword)).setText("");
    }
}
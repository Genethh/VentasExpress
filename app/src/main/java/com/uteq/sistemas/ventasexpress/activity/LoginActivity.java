package com.uteq.sistemas.ventasexpress.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.uteq.sistemas.ventasexpress.R;
import com.uteq.sistemas.ventasexpress.model.Cliente;
import com.uteq.sistemas.ventasexpress.model.Empleado;
import com.uteq.sistemas.ventasexpress.utils.Constants;
import com.uteq.sistemas.ventasexpress.utils.WebServ.Asynchtask;
import com.uteq.sistemas.ventasexpress.utils.WebServ.WebService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements Asynchtask {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @BindView(R.id.input_email)
    EditText _emailText;
    @BindView(R.id.input_password)
    EditText _passwordText;
    @BindView(R.id.btn_login)
    Button _loginButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

    }

    public void login() {
        Constants.cliente = null;
        Constants.empleado = null;
        _loginButton.setEnabled(false);
        if (!validate()) {
            onLoginFailed();
            return;
        }
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        ConectWSLogin(email, password);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the VendedorActivity
        moveTaskToBack(true);
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Usuario o contraseña incorrecta", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || email.length() < 4) {
            _emailText.setError("Ingrese un usuario correcto");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty()) {
            _passwordText.setError("Ingrese su clave");
            valid = false;
        } else if (password.length() < 6) {
            _passwordText.setError("La clave debe ser mayor a 6 caracteres");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    private void ConectWSLogin(String usuario, String clave) {
        Map<String, String> params = new HashMap<>();
        params.put("login", "1");
        params.put("usuario", usuario);
        params.put("clave", clave);
        WebService ws = new WebService(Constants.WS_USUARIO, params, this, (Asynchtask) LoginActivity.this);
        ws.execute("");
    }

    @Override
    public void processFinish(String result) throws JSONException {
        Log.e("ErrorLogin", "Error " + result);
        JSONObject obj = new JSONObject(result);
        String r = obj.getString("resultado");
        if (r.equals("1")) {
            String tipo = obj.getString("tipo");
            if (tipo.equals("vendedor")) {
                JSONObject empleado = new JSONObject(obj.getString("detalle"));
                Constants.empleado = new Empleado(empleado);
                Intent intent = new Intent(LoginActivity.this, VendedorActivity.class);
                startActivity(intent);
                finish();
            } else if (tipo.equals("cliente")) {
                JSONObject cliente = new JSONObject(obj.getString("detalle"));
                Constants.cliente = new Cliente(cliente);
                Intent intent = new Intent(LoginActivity.this, ClienteActivity.class);
                startActivity(intent);
                finish();
            } else if (tipo.equals("admin")) {
                Intent intent = new Intent(LoginActivity.this, AdministradorActivity.class);
                startActivity(intent);
                finish();
            }
        } else {
            onLoginFailed();
        }
    }
}
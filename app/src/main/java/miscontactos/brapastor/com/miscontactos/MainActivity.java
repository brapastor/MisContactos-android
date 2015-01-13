package miscontactos.brapastor.com.miscontactos;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import miscontactos.brapastor.com.miscontactos.util.TextChangedListener;

public class MainActivity extends ActionBarActivity {

    private EditText txtNombre, txtTelefono, txtEmail, txtDireccion;
    private Button btn_agregar;

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializarComponentesUI();

    }

    private void inicializarComponentesUI() {
        txtNombre = (EditText) findViewById(R.id.cmpNombre);
        txtTelefono = (EditText) findViewById(R.id.cmpTelefono);
        txtEmail = (EditText) findViewById(R.id.cmpEmail);
        txtDireccion = (EditText) findViewById(R.id.cmpDireccion);

        txtNombre.addTextChangedListener(new TextChangedListener(){
            @Override
            public void onTextChanged(CharSequence seq, int start, int before, int count) {
                btn_agregar = (Button) findViewById(R.id.btnAgregar);
                btn_agregar.setEnabled(!seq.toString().trim().isEmpty());
            }
        });


    }

    public void onClick(View view) {
        String mesg = String.format("%s ha sido agregado a la lista!", txtNombre.getText());
        Toast.makeText(this,mesg,Toast.LENGTH_SHORT).show();
        btn_agregar.setEnabled(false);
        limpiarCampos();
    }

    private void limpiarCampos() {
        txtNombre.getText().clear();
        txtTelefono.getText().clear();
        txtEmail.getText().clear();
        txtDireccion.getText().clear();
        txtNombre.requestFocus();
    }
}

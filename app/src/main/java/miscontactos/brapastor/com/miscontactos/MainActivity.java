package miscontactos.brapastor.com.miscontactos;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import java.util.ArrayList;

import miscontactos.brapastor.com.miscontactos.util.ContactListAdapter;
import miscontactos.brapastor.com.miscontactos.util.Contacto;
import miscontactos.brapastor.com.miscontactos.util.TextChangedListener;

public class MainActivity extends ActionBarActivity {

    private EditText txtNombre, txtTelefono, txtEmail, txtDireccion;
    private Button btn_agregar;
    private ArrayAdapter<Contacto> adapter;
    private ListView contactsListView;
    private ImageView imgViewContacto;
    private TabHost tabHost;
    private int request_code = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializarComponentesUI();
        iniciarlizarListContactos();
        iniciarlizarTabs();

    }

    private void iniciarlizarListContactos() {
        adapter = new ContactListAdapter(this, new ArrayList<Contacto>());
        contactsListView.setAdapter(adapter);
    }
    private void iniciarlizarTabs() {
       tabHost =(TabHost) findViewById(R.id.tabHost);
        tabHost.setup(); // decimos a android que las pestañas que los tags van a ser modificados

        TabHost.TabSpec spec = tabHost.newTabSpec("tab1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Crear");
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("tab2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Lista");
        tabHost.addTab(spec);
    }

    private void inicializarComponentesUI() {
        txtNombre = (EditText) findViewById(R.id.cmpNombre);
        txtTelefono = (EditText) findViewById(R.id.cmpTelefono);
        txtEmail = (EditText) findViewById(R.id.cmpEmail);
        txtDireccion = (EditText) findViewById(R.id.cmpDireccion);
        imgViewContacto = (ImageView) findViewById(R.id.imgViewContacto);
        contactsListView = (ListView) findViewById(R.id.listView);

        txtNombre.addTextChangedListener(new TextChangedListener(){
            @Override
            public void onTextChanged(CharSequence seq, int start, int before, int count) {
                btn_agregar = (Button) findViewById(R.id.btnAgregar);
                btn_agregar.setEnabled(!seq.toString().trim().isEmpty());
            }
        });
    }

    public void onClick(View view) {
        AgregarCantacto(
                txtNombre.getText().toString(),
                txtTelefono.getText().toString(),
                txtEmail.getText().toString(),
                txtDireccion.getText().toString(),
                (Uri) imgViewContacto.getTag() //obtenemos el atributo TAG con la Uri de la imagen
        );

        String mesg = String.format("%s ha sido agregado a la lista!", txtNombre.getText());
        Toast.makeText(this,mesg,Toast.LENGTH_SHORT).show();
        btn_agregar.setEnabled(false);
        limpiarCampos();

    }



    private void AgregarCantacto(String nombre, String telefono, String email, String direccion, Uri imageUri) {

        Contacto nuevo= new Contacto(nombre,telefono,email,direccion, imageUri);
        adapter.add(nuevo);
    }

    private void limpiarCampos() {
        txtNombre.getText().clear();
        txtTelefono.getText().clear();
        txtEmail.getText().clear();
        txtDireccion.getText().clear();
        //Restablesca la imagen predeterminado del contacto
        imgViewContacto.setImageResource(R.drawable.ic_launcher);
        txtNombre.requestFocus();
    }

    public void onImgclick(View view) {
        Intent intent = null;
        // Verificamos la versión de la plataforma
        if (Build.VERSION.SDK_INT < 19) {
            // Android JellyBean 4.3 y anteriores
            intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
        } else {
            // Android KitKat 4.4 o superior
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
        }
        intent.setType("image/*");
        startActivityForResult(intent, request_code);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == request_code) {
            imgViewContacto.setImageURI(data.getData());
            // Utilizamos el atributo TAG para almacenar la Uri al archivo seleccionado
            imgViewContacto.setTag(data.getData());
        }
    }
}

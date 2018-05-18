package com.android.efrei.fantasport.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.efrei.fantasport.R;
import com.android.efrei.fantasport.bd.MatchDAO;
import com.android.efrei.fantasport.bd.SQLiteConnexion;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private static final String TAG = "MainActivity";
    private final MatchDAO matchDAO = new MatchDAO();
    String mCurrentPhotoPath;
    private TextView msgTest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "...onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);





        msgTest = (TextView) findViewById(R.id.msgtest);

        matchDAO.setDb(SQLiteConnexion.getInstance().ouvrir(MainActivity.this));
        /*Match premierMatch = new Match("Joe","Marie","2","7","124","Toulouse");
        Match premierMatch2 = new Match("Jean","Marc","6","3","60","Villejuif");
        Match premierMatch3 = new Match("Louis","Pierre","4","6","12","Paris");
        Match premierMatch4 = new Match("Jack","Ouille","9","1","16","Rome");
        Match premierMatch6 = new Match("John","Wayne","6","2","115","Londre");
        Match premierMatch7 = new Match("Boum","Bam","5","1","115","Gap");
        matchDAO.ajouter(premierMatch);
        matchDAO.ajouter(premierMatch2);
        matchDAO.ajouter(premierMatch3);
        matchDAO.ajouter(premierMatch4);
        matchDAO.ajouter(premierMatch6);
        matchDAO.ajouter(premierMatch7);
        matchDAO.ajouter(premierMatch2);*/
        SQLiteConnexion.getInstance().fermer();

    }


    @Override
    protected void onResume() {
        Log.i(TAG, "...onResume");
        super.onResume();

        if(baseEstVide()){
            msgTest.setText("Hey la base est vide !");
        }else {
            matchDAO.setDb(SQLiteConnexion.getInstance().ouvrir(MainActivity.this));
            msgTest.setText("Hey la base est pas vide !" + matchDAO.recupererParId(1).getJoueur1());
            SQLiteConnexion.getInstance().fermer();


        }


    }
    @Override
    public void onBackPressed() {
        Log.i(TAG, "...onBackPressed");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(TAG, "...onCreateOptionsMenu");
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(TAG, "...onOptionsItemSelected");

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            dispatchTakePictureIntent();
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_map) {
            startActivity(new Intent(MainActivity.this, MapsActivity.class));

        } else if (id == R.id.nav_stats) {
            startActivity(new Intent(MainActivity.this, ListeMatchActivity.class));


        } else if (id == R.id.nav_contact) {

        } else if (id == R.id.nav_apropos) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.e(TAG,ex.toString());
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }


    /**
     * Permet de dire s'il y a des items en base de données
     *
     * @return true si vide false sinon
     */
    private boolean baseEstVide() {
        matchDAO.setDb(SQLiteConnexion.getInstance().ouvrir(this));
        int nb = matchDAO.nbLignes();
        SQLiteConnexion.getInstance().fermer();

        return nb == 0;
    }

    public void lancerCamera(View view) {
        dispatchTakePictureIntent();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            //Bundle extras = data.getExtras();
            //Bitmap imageBitmap = (Bitmap) extras.get("data");
            //mImageView.setImageBitmap(imageBitmap);
            galleryAddPic();
        }
    }
}

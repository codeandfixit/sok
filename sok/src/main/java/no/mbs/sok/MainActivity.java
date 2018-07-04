package no.mbs.sok;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.admin.myapplication.R;

import java.util.logging.Level;
import java.util.logging.Logger;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Logger logger = Logger.getLogger(MainActivity.class.getName());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view ->
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        );

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        initButtonGaupen();
        initButtonBrenderup();
        initSok();
    }

    private void initButtonGaupen() {
        Button gaupen = findViewById(R.id.gaupen);
        gaupen.setOnClickListener(listner -> {

            TextView sokresultat = findViewById(R.id.sokresultat);

            SearchView query = findViewById(R.id.sok);
            String sok = query.getQuery().toString();
            logger.log(Level.INFO, "* * * * initButtonGaupen: " + sok);
            if(isCarNumberSok(sok)) {
                sokresultat.setText("");
                String resultat = "";
                try {
                    resultat = new Tilhengerkakulator().execute(sok, "DP4565", "").get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                logger.log(Level.WARNING, "* * * * resultat: " + resultat);

                sokresultat.setText(resultat);
                if(resultat != null && resultat.length() > 0 ) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(sokresultat.getWindowToken(), 0);
                }

            }
        });
    }

    private void initButtonBrenderup() {
        Button gaupen = findViewById(R.id.brenderup);
        gaupen.setOnClickListener(listner -> {

            TextView sokresultat = findViewById(R.id.sokresultat);

            SearchView query = findViewById(R.id.sok);
            String sok = query.getQuery().toString();
            logger.log(Level.INFO, "* * * * initButtonBrenderup: " + sok);
            if(isCarNumberSok(sok)) {
                sokresultat.setText("");
                String resultat = "";
                try {
                    resultat = new Tilhengerkakulator().execute(sok, "LU5159", "").get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                logger.log(Level.WARNING, "* * * * resultat: " + resultat);

                sokresultat.setText(resultat);
                if(resultat != null && resultat.length() > 0 ) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(sokresultat.getWindowToken(), 0);
                }

            }
        });
    }

    private void initSok() {
        try {
            SearchView sv = findViewById(R.id.sok);
            sv.setFocusable(true);
            sv.setActivated(true);
            sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                  @Override
                  public boolean onQueryTextSubmit(String query) {
                      return false;
                  }

                  @Override
                  public boolean onQueryTextChange(String text) {
                      logger.log(Level.INFO, "* * * * onQueryTextChange: " + text);

                      TextView sokresultat = findViewById(R.id.sokresultat);
                      if(isPhoneNupmberSok(text)) {

                          sokresultat.setText("");
                          String resultat = "";
                          try {
                              resultat = new SokNummerOpplysning().execute(text, null, "").get();
                          } catch (Exception e) {
                              e.printStackTrace();
                          }
                          logger.log(Level.WARNING, "* * * * resultat: " + resultat);

                          sokresultat.setText(resultat);
                          if(resultat != null && resultat.length() > 0 ) {
                              InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                              imm.hideSoftInputFromWindow(sokresultat.getWindowToken(), 0);
                          }
                      }
                      else {
                          sokresultat.setText("");
                      }


                      return false;
                  }
              }
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isCarNumberSok(String text) {
        return isNotNull(text)  && !isNumber(text) && text.length() > 6  && !isNumber(text.substring(0,1)) ;
    }

    private boolean isPhoneNupmberSok(String newText) {
        return isNotNull(newText)  && isNumber(newText) && newText.length() == 8;
    }

    private boolean isNumber(String value) {
        boolean resultat = false;
        try {
            Integer.valueOf(value);
            resultat = true;
        } catch (NumberFormatException e){
            //ignore
        }
        return resultat;
    }

    private boolean isNotNull(String value) {
        return value != null;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
    public boolean onNavigationItemSelected(final MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        final String res;

        switch (id) {
            case R.id.nav_camera:
                // Handle the camera action
                break;
            case R.id.nav_gallery:

                break;
            case R.id.nav_slideshow:

                break;
            case R.id.nav_manage:

                break;
            case R.id.nav_share:

                break;
            case R.id.nav_send:
                try {
                    res = new Tilhengerkakulator().execute("", null, "").get();


                    Snackbar.make(findViewById(R.id.nav_view), res, Snackbar.LENGTH_INDEFINITE)
                            .setAction("Action", null).show();


                } catch (Throwable e) {
                    e.printStackTrace();
                }
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }


}

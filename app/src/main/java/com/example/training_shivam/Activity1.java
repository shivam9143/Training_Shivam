package com.example.training_shivam;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

public class Activity1 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    Toolbar toolbar;
    NavController navController;
    DrawerLayout drawerLayout;
    AppBarConfiguration appBarConfiguration;
    NavigationView navView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
        {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity1);

        /*   Declaring References */

        toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout=findViewById(R.id.drawer_layout);
        navController = Navigation.findNavController(this, R.id.my_nav_host_fragment);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        navView = findViewById(R.id.nav_view);

        /*   Attaching Components(Nav View & Action Bar) with Navigation Drawer */
        /*   Read more about all these  */
        //NavigationUI.setupWithNavController(toolbar, navController);  /* Adding this disables hamburger navigation drawer button! */

        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout);
        NavigationUI.setupWithNavController(navView, navController);

            /*Adding Navigation Drawer Item Listner*/

        navView.setNavigationItemSelectedListener(this);

        }



    @Override
    public boolean onSupportNavigateUp()
        {
        NavController navController = Navigation.findNavController(this, R.id.my_nav_host_fragment);
        return NavigationUI.navigateUp(navController, drawerLayout)
                || super.onSupportNavigateUp();
        }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
        {

        /*  Closing Drawer immediately after NavItemSelection*/

        drawerLayout.closeDrawers();

        switch (menuItem.getItemId())
            {
                case R.id.frag1:
                    navController.navigate(R.id.frag1);
                break;
                case R.id.frag2:
                    navController.navigate(R.id.frag2);
                break;
                case  R.id.frag3:
                    navController.navigate(R.id.frag3);
                break;
                case   R.id.frag4:
                    navController.navigate(R.id.frag4);
                break;
                default:
                break;
            }
            return true;
        }


}

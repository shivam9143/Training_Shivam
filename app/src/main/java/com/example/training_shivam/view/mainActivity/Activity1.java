package com.example.training_shivam.view.mainActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.training_shivam.R;
import com.example.training_shivam.model.User;
import com.example.training_shivam.utils.DatabaseHelper;
import com.example.training_shivam.utils.DrawerInterface;
import com.example.training_shivam.utils.ImageConverter;
import com.example.training_shivam.utils.onBackPressed;
import com.google.android.material.navigation.NavigationView;

public class Activity1 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, DrawerInterface
{
    Toolbar toolbar;
    NavController navController;
    DrawerLayout drawerLayout;
    AppBarConfiguration appBarConfiguration;
    NavigationView navView;
    SharedPreferences sharedPref;
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
        View headerLayout = navView.getHeaderView(0);

        if(getIntent().getExtras()!=null)
        {
            int intentFragment = getIntent().getExtras().getInt("fragmentToOpen");
            switch (intentFragment)
            {
                case 3:
                    navController.navigate(R.id.frag3);
            }
        }


        /*   Attaching Components(Nav View & Action Bar) with Navigation Drawer */
        /*   Read more about all these  */
        //NavigationUI.setupWithNavController(toolbar, navController);  /* Adding this disables hamburger navigation drawer button! */
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout);
        NavigationUI.setupWithNavController(navView, navController);
            /*Adding Navigation Drawer Item Listner*/
        navView.setNavigationItemSelectedListener(this);
        sharedPref = getSharedPreferences("Login_State", Context.MODE_PRIVATE);
        User lgn = new DatabaseHelper(this).getLoggedInUserDetails(sharedPref.getString("Email", "0"));
        if(sharedPref.getInt("LoggedIn",0)==1)
        {
            navController.navigate(R.id.frag1);
            TextView headerText = headerLayout.findViewById(R.id.nav_header_textView);
            headerText.setText(lgn.getEmail());
            ImageView headerPic = headerLayout.findViewById(R.id.nav_header_imageView);
            if(!lgn.getPic().equals("demoFilePath"))
            headerPic.setImageBitmap(ImageConverter.getRoundedCornerBitmap(BitmapFactory.decodeFile(lgn.getPic()), 100));
//            navController.popBackStack();
        }
        else
        {
            navController.navigate(R.id.frag3);
            //findNavController(fragment).navigate(R.id.action_signInFragment_to_usersFragment)
            //navController.navigate(R.id.action_signupFragment_to_profileFragment);
        }
        }

//    @Override
//    public void onBackPressed() {
//        Fragment f = getSupportFragmentManager().findFragmentById(R.id.frag3);
//        if(f instanceof Fragment3)
//        {
//            finish();
//        }
//        super.onBackPressed();
//
//    }

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
                case    R.id.frag5:
                   sharedPref.edit().putInt("LoggedIn",0).apply();
                   sharedPref.edit().clear();

                   finish();
                default:
                break;
            }
            return true;
        }


    @Override
    public void setDrawerEnabled(boolean enabled) {
        int lockMode = enabled ? DrawerLayout.LOCK_MODE_UNLOCKED :
                DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
        drawerLayout.setDrawerLockMode(lockMode);
    }

}

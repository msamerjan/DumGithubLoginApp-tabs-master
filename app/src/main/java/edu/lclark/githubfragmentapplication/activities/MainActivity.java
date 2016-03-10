package edu.lclark.githubfragmentapplication.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.lclark.githubfragmentapplication.R;
import edu.lclark.githubfragmentapplication.fragments.LoginFragment;
import edu.lclark.githubfragmentapplication.fragments.MainActivityFragment;
import edu.lclark.githubfragmentapplication.fragments.UserFragment;
import edu.lclark.githubfragmentapplication.models.GithubUser;

public class MainActivity extends AppCompatActivity implements MainActivityFragment.FollowerSelectedListener, UserFragment.UserListener, LoginFragment.LoginListener {

    @Bind(R.id.activity_main_framelayout)
    FrameLayout mFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main_framelayout, new LoginFragment());
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void onFollowerSelected(GithubUser follower) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main_framelayout, UserFragment.newInstance(follower));
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onTabbedFollowerButtonClicked(GithubUser user) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main_framelayout, MainActivityFragment.newInstance(user));
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onLoginResult(GithubUser user) {
        if (user == null) {
            Toast toast = Toast.makeText(this, R.string.user_not_found, Toast.LENGTH_SHORT);
            toast.show();
            findViewById(R.id.fragment_main_login_button).setEnabled(true);
            findViewById(R.id.progress_bar).setVisibility(View.GONE);
            return;
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main_framelayout, UserFragment.newInstance(user));
        transaction.commit();
    }
}

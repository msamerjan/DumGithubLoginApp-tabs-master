package edu.lclark.githubfragmentapplication;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import edu.lclark.githubfragmentapplication.fragments.LoginFragment;
import edu.lclark.githubfragmentapplication.models.GithubUser;

public class UserDetailAsyncTask extends AsyncTask<String, Integer, GithubUser> {

    public static final String TAG = UserDetailAsyncTask.class.getSimpleName();
    LoginFragment.LoginListener Listener;

    public UserDetailAsyncTask(LoginFragment.LoginListener mListener) {
        Listener = mListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d(TAG, "UserASyncTask started");
    }

    @Override
    protected GithubUser doInBackground(String... params) {

        StringBuilder userDetailBuilder = new StringBuilder();
        GithubUser user = null;

        if (params.length == 0) {
            return null;
        }

        String userId = params[0];
        try {
            URL url = new URL("https://api.github.com/users/" + userId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            InputStreamReader inputStream = new InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(inputStream);
            String line;

            if (isCancelled())
                return null;

            while ((line = reader.readLine()) != null) {
                userDetailBuilder.append(line);

                if (isCancelled())
                    return null;
            }

            user = new Gson().fromJson(userDetailBuilder.toString(), GithubUser.class);

            if (isCancelled())
                return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    protected void onPostExecute(GithubUser githubUser) {
        super.onPostExecute(githubUser);

        Listener.onLoginResult(githubUser);
    }


    @Override
    protected void onCancelled() {
        super.onCancelled();
        Log.d(TAG, "Async task cancelled!");
    }
}

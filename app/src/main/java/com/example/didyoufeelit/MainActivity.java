package com.example.didyoufeelit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2016-01-01&endtime=2016-05-02&minfelt=50&minmagnitude=5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Perform the HTTP request for earthquake data and process the response.
        //Event earthquake = Utils.fetchEarthquakeData(USGS_REQUEST_URL);
        // Update the information displayed to the user.
        //updateUi(earthquake);

        // Create an {@link AsyncTask} to perform the HTTP request to the given URL
        // on a background thread. When the result is received on the main UI thread,
        // then update the UI.
        EarthquakeAsyncTask task = new EarthquakeAsyncTask();
        task.execute(USGS_REQUEST_URL);
    }

    private void updateUi(Event earthquake) {
        TextView titleTextView = (TextView) findViewById(R.id.title);
        titleTextView.setText(earthquake.title);

        TextView tsunamiTextView = (TextView) findViewById(R.id.number_of_people);
        tsunamiTextView.setText(getString(R.string.num_people_felt_it, earthquake.numOfPeople));

        TextView magnitudeTextView = (TextView) findViewById(R.id.perceived_magnitude);
        magnitudeTextView.setText(earthquake.perceivedStrength);
    }

    private class EarthquakeAsyncTask extends AsyncTask<String, Void, Event> {
        @Override
        protected Event doInBackground(String... urls) {
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            Event result = Utils.fetchEarthquakeData(urls[0]);
            return result;
        }

        protected void onPostExecute(Event result) {
            // If there is no result, do nothing.
            if (result == null) {
                return;
            }

            updateUi(result);
        }
    }
}



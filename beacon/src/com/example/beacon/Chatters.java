package com.example.beacon;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.SupportMapFragment;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

/**
 * Created by Shreyash Appikatla on 11-06-2015.
 */
public class Chatters extends Fragment {

    private ParseQueryAdapter<User> usersQuery;
    ListView recyclerView;
    View reminderView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (reminderView != null) {
            ViewGroup parent = (ViewGroup) reminderView.getParent();
            if (parent != null) {
                parent.removeView(reminderView);
            }
        }
        try {
            reminderView = inflater.inflate(R.layout.chatters, null, false);
        }catch(InflateException e){

        }

        ParseQueryAdapter.QueryFactory<User> factory =
                new ParseQueryAdapter.QueryFactory<User>() {
                    public ParseQuery<User> create() {
                        ParseQuery<User> query = User.getQuery();
                        query.include("user");
                        query.orderByDescending("createdAt");
                        return query;
                    }
                };

        // Set up the query adapter
        usersQuery = new ParseQueryAdapter<User>(getActivity(), factory) {
            @Override
            public View getItemView(User post, View view, ViewGroup parent) {
                Log.e("hey", "sup");
                if (view == null) {
                    view = View.inflate(getContext(), R.layout.chat_view, null);
                }
                TextView usernameView = (TextView) view.findViewById(R.id.chat_id);
                usernameView.setText(post.getUser().toString());
                return view;
            }
        };
        usersQuery.setTextKey("text");

        // Disable automatic loading when the adapter is attached to a view.
        usersQuery.setAutoload(false);

        // Disable pagination, we'll manage the query limit ourselves
        usersQuery.setPaginationEnabled(false);

        recyclerView = (ListView) reminderView.findViewById(R.id.chat_list);
        recyclerView.setAdapter(usersQuery);
        return recyclerView;
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Called when the Activity is restarted, even before it becomes visible.
     */
    @Override
    public void onStart() {
        super.onStart();
    }

    /*
     * Called when the Activity is resumed. Updates the view.
     */
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(recyclerView!=null)
        ((ViewGroup)recyclerView.getParent()).removeAllViews();
    }
}

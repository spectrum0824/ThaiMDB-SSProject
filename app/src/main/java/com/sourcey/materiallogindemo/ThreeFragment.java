package com.sourcey.materiallogindemo;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ThreeFragment extends Fragment {

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mReviewsRef = mRootRef.child("reviews");
    long score = 5;
    int posterIndex = 0;
    Button createBt;
    SeekBar scoreBar;
    AutoCompleteTextView _title;
    EditText _des;
    TextView _score;
    String[] movies;
    String[] images;


    public void refresh(){

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(ThreeFragment.this).attach(ThreeFragment.this).commit();

    }
    public ThreeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_three, container, false);
        createBt = (Button) view.findViewById(R.id.btn_create);
        createBt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                create();
            }
        });
        movies = getResources().getStringArray(R.array.movies_array);
        images = getResources().getStringArray(R.array.poster_array);
        _title = (AutoCompleteTextView) view.findViewById(R.id.input_title);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, movies);
        _title.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        _des = (EditText) view.findViewById(R.id.input_des);
        _score = (TextView) view.findViewById(R.id.score_tv);
        scoreBar = (SeekBar) view.findViewById(R.id.scoreBar);
        scoreBar.setProgress(5);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                scoreBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress,
                                                  boolean fromUser) {
                        score = progress;
                        _score.setText("You give the score : " + score);

                            }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        // TODO Auto-generated method stub

                    }

                });

            }
        });
        return view;
    }

    public void create(){

        if(validate()) {



            mReviewsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    DatabaseReference mUsersRef = mRootRef.child("users");

                    mUsersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            String id = MainActivity.id;

                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                if (id.equals(postSnapshot.child("id").getValue())) {
                                    MainActivity.name = postSnapshot.child("name").getValue().toString();
                                }

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    createBt.setEnabled(false);

                    final ProgressDialog progressDialog = new ProgressDialog(getActivity(),
                            R.style.AppTheme_Dark_Dialog);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Posting Review...");
                    progressDialog.show();

                    String title = _title.getText().toString();
                    for (int i = 0; i < movies.length; i++) {
                        if (title.equals(movies[i])){
                            posterIndex = i;
                        }
                    }
                    String url = images[posterIndex];
                    String des = _des.getText().toString();
                    String id = MainActivity.id;
                    String name = MainActivity.name;
                    long score = scoreBar.getProgress();
                    long relia = MainActivity.relia;

                    mReviewsRef.push().setValue(new Review(title, name, url, score, des, id, relia));

                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                public void run() {
                                    onSuccessCreate();

                                    progressDialog.dismiss();
                                }
                            }, 3000);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }




            });

        }else {


        }


    }



    public void onSuccessCreate(){
        createBt.setEnabled(true);
        Toast.makeText(getActivity().getBaseContext(), "You created the review", Toast.LENGTH_LONG).show();
        refresh();
    }

    public boolean validate() {
        boolean valid = true;

        String title = _title.getText().toString();
        String des = _des.getText().toString();

        if (title.isEmpty() || title.length() < 3) {
            _title.setError("at least 3 characters");
            valid = false;
        } else {
            _title.setError(null);
        }



        if (des.isEmpty() || des.length() < 4) {
            _des.setError("at least 4 characters");
            valid = false;
        } else {
            _des.setError(null);
        }



        return valid;
    }
}







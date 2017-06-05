package com.sourcey.materiallogindemo;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OneFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class OneFragment extends Fragment  {

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mReviewsRef = mRootRef.child("reviews");

    private Context mContext;
    LinearLayout mLinearLayout;
    LinearLayout innerLayout;
    LinearLayout btLayout;
    String nameGlobal;
    DataSnapshot nowSnap;
    String nowKey;
    String idNow;
    Button reBt;

    public void refresh(){

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(OneFragment.this).attach(OneFragment.this).commit();

    }




    public OneFragment() {
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
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        mLinearLayout = (LinearLayout) view.findViewById(R.id.rl);
        mContext = getActivity();

        reBt = (Button) view.findViewById(R.id.btn_refresh);
        reBt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                refresh();
            }
        });


        mReviewsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String titleStr = postSnapshot.child("title").getValue().toString();
                    String nameStr = postSnapshot.child("name").getValue().toString();
                    String desStr = postSnapshot.child("des").getValue().toString();
                    String scoreInt = postSnapshot.child("score").getValue().toString();
                    nameGlobal = nameStr;
                    nowSnap = postSnapshot;
                    nowKey = postSnapshot.getKey();


                    long relia = (long) postSnapshot.child("relia").getValue();
                    String urlStr = postSnapshot.child("url").getValue().toString();

                    // Initialize a new CardView
                    CardView card = new CardView(mContext);

                    // Set the CardView layoutParams
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );

                    LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );

                    LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );

                    innerLayout = new LinearLayout(mContext);

                    innerLayout.setLayoutParams(params2);
                    innerLayout.setOrientation(LinearLayout.VERTICAL);




                    params.setMargins(20, 40, 20, 0);
                    params2.bottomMargin = 15;
                    params2.leftMargin = 20;
                    params2.rightMargin = 20;
                    params2.topMargin = 15;

                    params3.bottomMargin = 5;
                    params3.topMargin = 5;
                    params3.leftMargin = 20;
                    params3.rightMargin = 20;

                    card.setLayoutParams(params);

                    // Set CardView corner radius
                    card.setRadius(9);

                    // Set cardView content padding
                    card.setContentPadding(20, 20, 20, 20);

                    // Set a background color for CardView
                    card.setCardBackgroundColor(Color.parseColor("#333333"));

                    // Set the CardView maximum elevation
                    card.setMaxCardElevation(150);

                    // Set CardView elevation
                    card.setCardElevation(90);

                    // Initialize a new TextView to put in CardView
                    TextView title = new TextView(mContext);
                    String scoreStr = "Score : ";

                    title.setText(titleStr + " ----------> " + scoreStr + scoreInt);
                    title.setLayoutParams(params2);
                    title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                    title.setTextColor(Color.parseColor("#FFFFFF"));

                    TextView name = new TextView(mContext);
                    String reliaStr = "Reliability : ";
                    name.setText(nameStr + " ----------> " + reliaStr + relia + "⋆");
                    name.setLayoutParams(params2);
                    name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    name.setTextColor(Color.parseColor("#CCCCCC"));

                    ImageView img = new ImageView(mContext);
                    img.setLayoutParams(params);
                    Picasso.with(mContext).load(urlStr).into(img);

                    TextView des = new TextView(mContext);
                    des.setText(desStr);
                    des.setLayoutParams(params2);
                    des.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    des.setTextColor(Color.parseColor("#FFFFFF"));

                    Button agButton = new Button(mContext);
                    agButton.setText("Agree");
                    agButton.setLayoutParams(params3);

                    agButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            long relia = (long) nowSnap.child("relia").getValue() + 10;
                            mReviewsRef.child(nowKey).child("relia").setValue(relia);
                            refresh();

                        }
                    });

                    Button dgButton = new Button(mContext);
                    dgButton.setText("Disagree");
                    dgButton.setLayoutParams(params3);

                    dgButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            long relia = (long) nowSnap.child("relia").getValue() - 10;
                            mReviewsRef.child(nowKey).child("relia").setValue(relia);
                            refresh();

                        }
                    });


                    // Put the TextView in CardView
                    innerLayout.addView(title);
                    innerLayout.addView(name);
                    innerLayout.addView(img);
                    innerLayout.addView(des);
                    innerLayout.addView(agButton);
                    innerLayout.addView(dgButton);
                    card.addView(innerLayout);
                    mLinearLayout.addView(card);


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return view;
    }





}
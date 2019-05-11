package com.sourcey.materiallogindemo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
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

import java.util.ArrayList;

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
    String idNow;
    String nowKey;
    ArrayList<DataSnapshot> allSnap = new ArrayList<DataSnapshot>();
    ArrayList<String> allKeys = new ArrayList<String>();
    Button reBt;
    long nowRelia;
    public void refresh(){

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(OneFragment.this).attach(OneFragment.this).commit();
        allSnap.clear();
        allKeys.clear();

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
            int viewCounter = 0;

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String titleStr = postSnapshot.child("title").getValue().toString();
                    String nameStr = postSnapshot.child("name").getValue().toString();
                    String desStr = postSnapshot.child("des").getValue().toString();
                    String scoreInt = postSnapshot.child("score").getValue().toString();
                    long reviewScore = (long) postSnapshot.child("relia").getValue();
                    nameGlobal = nameStr;
                    nowSnap = postSnapshot;
                    nowKey = postSnapshot.getKey();
                    allSnap.add(postSnapshot);
                    allKeys.add(postSnapshot.getKey());


                    long relia = nowRelia;
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
                    String scoreStr = "Score: ";

                    title.setText(titleStr + "\t|\t" + scoreStr + scoreInt + "/10");
                    title.setLayoutParams(params2);
                    title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                    title.setTextColor(Color.parseColor("#FFFFFF"));
                    TextView like = new TextView(mContext);
                    like.setText("This review got " + reviewScore + " Likes");
                    like.setLayoutParams(params2);
                    like.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
                    like.setTextColor(Color.parseColor("#CCCCCC"));


                    TextView name = new TextView(mContext);
                    name.setText("Reviewer: " + nameStr);
                    name.setLayoutParams(params2);
                    name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
                    name.setTextColor(Color.parseColor("#CCCCCC"));
                    name.setTypeface(null, Typeface.ITALIC);

                    ImageView img = new ImageView(mContext);
                    img.setLayoutParams(params);
                    Picasso.with(mContext).load(urlStr).into(img);
                    TextView headDes = new TextView(mContext);
                    headDes.setText("Review");
                    headDes.setLayoutParams(params2);
                    headDes.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
                    headDes.setTextColor(Color.parseColor("#CCCCCC"));
                    headDes.setTypeface(null, Typeface.ITALIC);

                    TextView des = new TextView(mContext);
                    des.setText(desStr);
                    des.setLayoutParams(params2);
                    des.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                    des.setTextColor(Color.parseColor("#FFFFFF"));

                    Button agButton = new Button(mContext);
                    agButton.setId(viewCounter);
                    agButton.setText("Like");
                    agButton.setLayoutParams(params3);
                    agButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            long relia = (long) allSnap.get(v.getId()).child("relia").getValue() + 1;
                            mReviewsRef.child(allKeys.get(v.getId())).child("relia").setValue(relia);
                            refresh();

                        }
                    });

                    final Button dgButton = new Button(mContext);
                    dgButton.setText("Dislike");
                    dgButton.setId(viewCounter);
                    dgButton.setLayoutParams(params3);
                    dgButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            long relia = (long) allSnap.get(v.getId()).child("relia").getValue() - 1;
                            mReviewsRef.child(allKeys.get(v.getId())).child("relia").setValue(relia);
                            refresh();

                        }
                    });


                    // Put the TextView in CardView
                    innerLayout.addView(title);
                    innerLayout.addView(like);
                    innerLayout.addView(name);
                    innerLayout.addView(img);
                    innerLayout.addView(headDes);
                    innerLayout.addView(des);
                    innerLayout.addView(agButton);
                    innerLayout.addView(dgButton);
                    card.addView(innerLayout);
                    mLinearLayout.addView(card);

                    viewCounter++;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return view;
    }





}
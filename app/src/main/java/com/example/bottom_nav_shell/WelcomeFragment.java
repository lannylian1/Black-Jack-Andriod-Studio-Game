package com.example.bottom_nav_shell;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;



public class WelcomeFragment extends Fragment {

    SharedPreferences pref, prefScore;
    Button btnStart1, btnHit1, btnStay1, btnInit;
    Intent intent;
    TextView tvWelcome;
    ImageView dealer1,dealer2,dealer3,dealer4,dealer5, player1,player2,player3,player4,player5;
    ArrayList<Integer> cards;
    int score = 0;
    int click_times = 0, dealerClick_time=0;

    int playerFirstTotal, playerSecondTotal, dealerFirstTotal, dealerSecondTotal,dealerThirdTotal, dealerFourthTotal;
    
    //background tap dismiss keys
    private Context mContext;
    private Activity mActivity;
    private RelativeLayout cLayout;


    public static WelcomeFragment newInstance() {
        return new WelcomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.welcome_fragment, container, false);

        /*all activity code here*/
        //add background tap to dismiss keyboard
        mContext = getContext().getApplicationContext();
        mActivity = getActivity();
        cLayout = root.findViewById(R.id.rl_welcome);
        cLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),0);
            }
        });

        //link dealer variable to dealer card id
        dealer1 = (ImageView) root.findViewById(R.id.top_left1);
        dealer2 = (ImageView) root.findViewById(R.id.top_left2);
        dealer3 = (ImageView) root.findViewById(R.id.top_left3);
        dealer4 = (ImageView) root.findViewById(R.id.top_left4);
        dealer5 = (ImageView) root.findViewById(R.id.top_left5);

        //link player variable to player card id
        player1 = (ImageView) root.findViewById(R.id.bottom_left1);
        player2 = (ImageView) root.findViewById(R.id.bottom_left2);
        player3 = (ImageView) root.findViewById(R.id.bottom_left3);
        player4 = (ImageView) root.findViewById(R.id.bottom_left4);
        player5 = (ImageView) root.findViewById(R.id.bottom_left5);

        //buttons
        btnStart1 = (Button) root.findViewById(R.id.btnStart);
        btnHit1 = (Button) root.findViewById(R.id.btnHit);
        btnStay1 = (Button) root.findViewById(R.id.btnStay);
        btnInit = (Button) root.findViewById(R.id.btnAgain);

        //add cards into array list
        cards = new ArrayList<>();
        for( int i = 1; i <= 52; i++) {
            cards.add(i);
        }

        //shuffle cards in array list
        Collections.shuffle(cards);

        //set the shared preferences for result name and score for Result fragment
        pref = getActivity().getSharedPreferences("result_name", Context.MODE_PRIVATE);
        prefScore = getActivity().getSharedPreferences("result_score", Context.MODE_PRIVATE);

        //link label variable with label id from design
        tvWelcome = (TextView) root.findViewById(R.id.lblWelcome);
        String str = pref.getString("name", null);

        //set label
        tvWelcome.setText("Hi " + str + ", Welcome, click START button to begin game ");

        //set button invisible except start button
        btnInit.setVisibility(View.INVISIBLE);
        btnHit1.setVisibility(View.INVISIBLE);
        btnStay1.setVisibility(View.INVISIBLE);


        //Button Play Again Listener
        btnInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pref = getActivity().getSharedPreferences("result_score", Context.MODE_PRIVATE);
                String str = pref.getString("name", null);
                tvWelcome.setText("Hi " + str + ", Welcome, click START button to begin game ");

                Collections.shuffle(cards);
                player1.setImageResource(R.drawable.back);
                player2.setImageResource(R.drawable.back);
                player3.setImageResource(R.drawable.back);
                player4.setImageResource(R.drawable.back);
                player5.setImageResource(R.drawable.back);
                dealer1.setImageResource(R.drawable.back);
                dealer2.setImageResource(R.drawable.back);
                dealer3.setImageResource(R.drawable.back);
                dealer4.setImageResource(R.drawable.back);
                dealer5.setImageResource(R.drawable.back);

                //set hit button times back to 0
                click_times = 0;
                //set stay button times back to 0;
                dealerClick_time = 0;
                //set visibility for three buttons
                btnStart1.setVisibility(View.VISIBLE);
                btnInit.setVisibility(View.INVISIBLE);
                btnStay1.setVisibility(View.INVISIBLE);
                btnHit1.setVisibility(View.INVISIBLE);

            }
        });


        //Button Start Listener
        btnStart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //first two cards value
                int pCard1= drawImage(cards.get(0),player1);
                int pCard2= drawImage(cards.get(1),player2);
                int dCard1= drawImage(cards.get(2),dealer1);
                int dCard2= drawImage(cards.get(3),dealer2);
                //set first two cars visible
                player1.setVisibility(View.VISIBLE);
                player2.setVisibility(View.VISIBLE);
                dealer1.setVisibility(View.VISIBLE);
                dealer2.setVisibility(View.VISIBLE);
                //set play again button invisible
                btnInit.setVisibility(View.INVISIBLE);
                btnHit1.setVisibility(View.VISIBLE);
                btnStay1.setVisibility(View.VISIBLE);

                //add values
                int pFirstSum= pCard1+pCard2;
                int dFirstSum= dCard1+dCard2;

                tvWelcome.setText("Player has: " + pFirstSum + ", Dealer has: "+ dFirstSum + " Please choose Stay or Hit");
                playerFirstTotal = pFirstSum;
                dealerFirstTotal = dFirstSum;


            }
        });

        //Button Hit Listener
        btnHit1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //increment the click times for hit button
                click_times++;

                //if player hit one time, the 3rd card will show
                if (click_times == 1) {
                    int pCard1= drawImage(cards.get(0),player1);
                    int pCard2= drawImage(cards.get(1),player2);
                    int dCard1= drawImage(cards.get(2),dealer1);
                    int dCard2= drawImage(cards.get(3),dealer2);
                    int pCard3 = drawImage(cards.get(4),player3);

                    player3.setVisibility(View.VISIBLE);
                    int pFirstSum= pCard1+pCard2;
                    int dFirstSum= dCard1+dCard2;
                    int pSecondSum =pFirstSum+pCard3;
                    playerSecondTotal = pSecondSum;
                    //check if player busted or not
                    if (pSecondSum > 21) {
                        score-=50;
                        tvWelcome.setText("Player has: " + pSecondSum+" Busted! Score: " +score);
                        String txtScore = Integer.toString(score);
                        SharedPreferences.Editor editor = prefScore.edit();
                        editor.putString("score", txtScore);
                        editor.commit();
                        Toast.makeText(mContext, "Player Score: " + txtScore, Toast.LENGTH_SHORT).show();

                        //set play again button visible
                        btnInit.setVisibility(View.VISIBLE);
                        //set hit button invisible
                        btnHit1.setVisibility(View.INVISIBLE);
                        btnStart1.setVisibility(View.INVISIBLE);
                        btnStay1.setVisibility(View.INVISIBLE);


                    } else if (pSecondSum == 21) {
                        tvWelcome.setText("Black Jack! Player Win!!");
                        score+=50;
                        Toast.makeText(mContext, "BlackJack!" + score, Toast.LENGTH_SHORT).show();
                        String txtScore = Integer.toString(score);
                        SharedPreferences.Editor editor = prefScore.edit();
                        editor.putString("score", txtScore);
                        editor.commit();
                        Toast.makeText(mContext, "Player Score: " + txtScore, Toast.LENGTH_SHORT).show();
                        //set play again button visible
                        btnInit.setVisibility(View.VISIBLE);
                        //set hit button invisible
                        btnHit1.setVisibility(View.INVISIBLE);
                        btnStart1.setVisibility(View.INVISIBLE);
                        btnStay1.setVisibility(View.INVISIBLE);
                    } else if (pSecondSum < 21) {
                        tvWelcome.setText("Player has: " + pSecondSum+" Dealer has: "+ dFirstSum + " Please continue");
                        btnStart1.setVisibility(View.INVISIBLE);
                    }
                    //second button click times statement
                } else if (click_times == 2) {
                    int pCard1= drawImage(cards.get(0),player1);
                    int pCard2= drawImage(cards.get(1),player2);
                    int dCard1= drawImage(cards.get(2),dealer1);
                    int dCard2= drawImage(cards.get(3),dealer2);
                    int pCard3 = drawImage(cards.get(4),player3);
                    int pCard4 = drawImage(cards.get(5),player4);

                    player4.setVisibility(View.VISIBLE);
                    int pFirstSum= pCard1+pCard2;
                    int dFirstSum= dCard1+dCard2;
                    int pSecondSum = pFirstSum+pCard3;
                    int pThirdSum = pSecondSum+pCard4;

                    //invisible stay button
                    btnStay1.setVisibility(View.INVISIBLE);
                    if (pThirdSum > 21) {
                        score-=50;
                        tvWelcome.setText("Player has: " + pThirdSum+" Busted! Score: " +score);
                        String txtScore = Integer.toString(score);
                        SharedPreferences.Editor editor = prefScore.edit();
                        editor.putString("score", txtScore);
                        editor.commit();
                        Toast.makeText(mContext, "Player Score: " + txtScore, Toast.LENGTH_SHORT).show();
                        //set play again button visible
                        btnInit.setVisibility(View.VISIBLE);
                        //set hit button invisible
                        btnHit1.setVisibility(View.INVISIBLE);
                        btnStart1.setVisibility(View.INVISIBLE);
                        btnStay1.setVisibility(View.INVISIBLE);
                    } else if (pThirdSum == 21) {
                        tvWelcome.setText("Black Jack! Player Win!!");
                        score+=50;
                        Toast.makeText(mContext, "BlackJack!" + score, Toast.LENGTH_SHORT).show();
                        String txtScore = Integer.toString(score);
                        SharedPreferences.Editor editor = prefScore.edit();
                        editor.putString("score", txtScore);
                        editor.commit();
                        Toast.makeText(mContext, "Player Score: " + txtScore, Toast.LENGTH_SHORT).show();
                        //set play again button visible
                        btnInit.setVisibility(View.VISIBLE);
                        //set hit button invisible
                        btnHit1.setVisibility(View.INVISIBLE);
                        btnStart1.setVisibility(View.INVISIBLE);
                        btnStay1.setVisibility(View.INVISIBLE);
                    } else if (pThirdSum < 21) {
                        tvWelcome.setText("Player has: " + pThirdSum+" Dealer has: "+ dFirstSum + " Please continue");
                        btnStart1.setVisibility(View.INVISIBLE);
                    }
                } else if (click_times==3) {
                    int pCard1= drawImage(cards.get(0),player1);
                    int pCard2= drawImage(cards.get(1),player2);
                    int dCard1= drawImage(cards.get(2),dealer1);
                    int dCard2= drawImage(cards.get(3),dealer2);
                    int pCard3 = drawImage(cards.get(4),player3);
                    int pCard4 = drawImage(cards.get(5),player4);
                    int pCard5 = drawImage(cards.get(6),player5);
                    player5.setVisibility(View.VISIBLE);
                    int pFirstSum= pCard1+pCard2;
                    int dFirstSum= dCard1+dCard2;
                    int pSecondSum = pFirstSum+pCard3;
                    int pThirdSum = pSecondSum+pCard4;
                    int pForthSum = pThirdSum+pCard5;
                    //invisible stay button
                    btnStay1.setVisibility(View.INVISIBLE);
                    if (pForthSum >21) {
                        score-=50;
                        tvWelcome.setText("Player has: " + pForthSum+" Busted! score: "+ score);
                        String txtScore = Integer.toString(score);
                        SharedPreferences.Editor editor = prefScore.edit();
                        editor.putString("score", txtScore);
                        editor.commit();
                        Toast.makeText(mContext, "Player Score: " + txtScore, Toast.LENGTH_SHORT).show();
                        //set play again button visible
                        btnInit.setVisibility(View.VISIBLE);
                        //set hit button invisible
                        btnHit1.setVisibility(View.INVISIBLE);
                        btnStart1.setVisibility(View.INVISIBLE);
                        btnStay1.setVisibility(View.INVISIBLE);


                    } else if(pForthSum > dFirstSum) {
                        score+=50;
                        tvWelcome.setText("Player has: " + pForthSum+ " Dealer:" + dFirstSum + "Player Win! Score: "+ score);
                        String txtScore = Integer.toString(score);
                        SharedPreferences.Editor editor = prefScore.edit();
                        editor.putString("score", txtScore);
                        editor.commit();
                        Toast.makeText(mContext, "Player Score: " + score, Toast.LENGTH_SHORT).show();
                        //set play again button visible
                        btnInit.setVisibility(View.VISIBLE);
                        //set hit button invisible
                        btnHit1.setVisibility(View.INVISIBLE);
                        btnStart1.setVisibility(View.INVISIBLE);
                        btnStay1.setVisibility(View.INVISIBLE);

                    }  else if(pForthSum < dFirstSum) {
                        score-=50;
                        tvWelcome.setText("Player has: " + pForthSum+ " Dealer:" + dFirstSum + "Dealer Win! Score: "+ score);
                        String txtScore = Integer.toString(score);
                        SharedPreferences.Editor editor = prefScore.edit();
                        editor.putString("score", txtScore);
                        editor.commit();
                        Toast.makeText(mContext, "Player Score: " + score, Toast.LENGTH_SHORT).show();
                        //set play again button visible
                        btnInit.setVisibility(View.VISIBLE);
                        //set hit button invisible
                        btnHit1.setVisibility(View.INVISIBLE);
                        btnStart1.setVisibility(View.INVISIBLE);
                        btnStay1.setVisibility(View.INVISIBLE);


                    }

                }

            }
        });

        //Button Stay Listener
        btnStay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dealerClick_time++;
                //first click stay button
                if (dealerClick_time == 1) {
                    int dCard3= drawImage(cards.get(7),dealer3);
                    dealerSecondTotal = dealerFirstTotal + dCard3;
                    dealer3.setVisibility(View.VISIBLE);
                    //set hit button invisible
                    btnHit1.setVisibility(View.INVISIBLE);
                    if (dealerSecondTotal == 21) {
                        tvWelcome.setText("Black Jack! Dealer Win!!");
                        score-=50;
                        Toast.makeText(mContext, "BlackJack!" + score, Toast.LENGTH_SHORT).show();
                        String txtScore = Integer.toString(score);
                        SharedPreferences.Editor editor = prefScore.edit();
                        editor.putString("score", txtScore);
                        editor.commit();
                        Toast.makeText(mContext, "Player Score: " + txtScore, Toast.LENGTH_SHORT).show();
                        //set play again button visible
                        btnInit.setVisibility(View.VISIBLE);
                        //set hit button invisible
                        btnHit1.setVisibility(View.INVISIBLE);
                        btnStart1.setVisibility(View.INVISIBLE);
                        btnStay1.setVisibility(View.INVISIBLE);
                    } else if (dealerSecondTotal > 21) {
                        score+=50;
                        tvWelcome.setText("Dealer has: " + dealerSecondTotal+" Busted! Score: "+ score);
                        String txtScore = Integer.toString(score);
                        SharedPreferences.Editor editor = prefScore.edit();
                        editor.putString("score", txtScore);
                        editor.commit();
                        Toast.makeText(mContext, "Player Score: " + txtScore, Toast.LENGTH_SHORT).show();
                        //set play again button visible
                        btnInit.setVisibility(View.VISIBLE);
                        //set hit button invisible
                        btnHit1.setVisibility(View.INVISIBLE);
                        btnStart1.setVisibility(View.INVISIBLE);
                        btnStay1.setVisibility(View.INVISIBLE);
                    } else if (dealerSecondTotal > 17 && dealerSecondTotal > playerFirstTotal) {
                        score-=50;
                        tvWelcome.setText("Dealer has: " + dealerSecondTotal+" Dealer Win! Score: "+ score);
                        String txtScore = Integer.toString(score);
                        SharedPreferences.Editor editor = prefScore.edit();
                        editor.putString("score", txtScore);
                        editor.commit();
                        Toast.makeText(mContext, "Player Score: " + txtScore, Toast.LENGTH_SHORT).show();
                        //set play again button visible
                        btnInit.setVisibility(View.VISIBLE);
                        //set hit button invisible
                        btnHit1.setVisibility(View.INVISIBLE);
                        btnStart1.setVisibility(View.INVISIBLE);
                        btnStay1.setVisibility(View.INVISIBLE);
                    } else {
                        tvWelcome.setText("Player: " + playerFirstTotal+ " Dealer: " + dealerSecondTotal +" Please Choose Stay or Hit");

                    }

                } else if (dealerClick_time == 2 ) {
                    int dCard4= drawImage(cards.get(8),dealer4);
                    dealerThirdTotal = dealerSecondTotal + dCard4;
                    dealer4.setVisibility(View.VISIBLE);
                    //set hit button invisible
                    btnHit1.setVisibility(View.INVISIBLE);
                    if (dealerThirdTotal == 21) {
                        tvWelcome.setText("Black Jack! Dealer Win!!");
                        score-=50;
                        Toast.makeText(mContext, "BlackJack!" + score, Toast.LENGTH_SHORT).show();
                        String txtScore = Integer.toString(score);
                        SharedPreferences.Editor editor = prefScore.edit();
                        editor.putString("score", txtScore);
                        editor.commit();
                        Toast.makeText(mContext, "Player Score: " + txtScore, Toast.LENGTH_SHORT).show();
                        //set play again button visible
                        btnInit.setVisibility(View.VISIBLE);
                        //set hit button invisible
                        btnHit1.setVisibility(View.INVISIBLE);
                        btnStart1.setVisibility(View.INVISIBLE);
                        btnStay1.setVisibility(View.INVISIBLE);
                    } else if (dealerThirdTotal > 21) {
                        score+=50;
                        tvWelcome.setText("Dealer has: " + dealerThirdTotal+" Busted! Score: "+ score);
                        String txtScore = Integer.toString(score);
                        SharedPreferences.Editor editor = prefScore.edit();
                        editor.putString("score", txtScore);
                        editor.commit();
                        Toast.makeText(mContext, "Player Score: " + txtScore, Toast.LENGTH_SHORT).show();
                        //set play again button visible
                        btnInit.setVisibility(View.VISIBLE);
                        //set hit button invisible
                        btnHit1.setVisibility(View.INVISIBLE);
                        btnStart1.setVisibility(View.INVISIBLE);
                        btnStay1.setVisibility(View.INVISIBLE);
                    } else if (dealerThirdTotal > 17 && dealerThirdTotal > playerFirstTotal) {
                        score-=50;
                        tvWelcome.setText("Dealer has: " + dealerThirdTotal+" Dealer Win! Score: "+ score);
                        String txtScore = Integer.toString(score);
                        SharedPreferences.Editor editor = prefScore.edit();
                        editor.putString("score", txtScore);
                        editor.commit();
                        Toast.makeText(mContext, "Player Score: " + txtScore, Toast.LENGTH_SHORT).show();
                        //set play again button visible
                        btnInit.setVisibility(View.VISIBLE);
                        //set hit button invisible
                        btnHit1.setVisibility(View.INVISIBLE);
                        btnStart1.setVisibility(View.INVISIBLE);
                        btnStay1.setVisibility(View.INVISIBLE);
                    } else {
                        tvWelcome.setText("Player: " + playerFirstTotal+ " Dealer: " + dealerThirdTotal +" Please continue");

                    }
                } else if (dealerClick_time == 3 ) {
                    int dCard5= drawImage(cards.get(9),dealer5);
                    dealerFourthTotal = dealerThirdTotal + dCard5;
                    dealer5.setVisibility(View.VISIBLE);
                    //set hit button invisible
                    btnHit1.setVisibility(View.INVISIBLE);
                    if (dealerFourthTotal == 21) {
                        tvWelcome.setText("Black Jack! Dealer Win!!");
                        score-=50;
                        Toast.makeText(mContext, "BlackJack!" + score, Toast.LENGTH_SHORT).show();
                        String txtScore = Integer.toString(score);
                        SharedPreferences.Editor editor = prefScore.edit();
                        editor.putString("score", txtScore);
                        editor.commit();
                        Toast.makeText(mContext, "Player Score: " + txtScore, Toast.LENGTH_SHORT).show();
                        //set play again button visible
                        btnInit.setVisibility(View.VISIBLE);
                        //set hit button invisible
                        btnHit1.setVisibility(View.INVISIBLE);
                        btnStart1.setVisibility(View.INVISIBLE);
                        btnStay1.setVisibility(View.INVISIBLE);
                    } else if (dealerFourthTotal > 21) {
                        score+=50;
                        tvWelcome.setText("Dealer has: " + dealerFourthTotal+" Busted! Score: "+ score);
                        String txtScore = Integer.toString(score);
                        SharedPreferences.Editor editor = prefScore.edit();
                        editor.putString("score", txtScore);
                        editor.commit();
                        Toast.makeText(mContext, "Player Score: " + txtScore, Toast.LENGTH_SHORT).show();
                        //set play again button visible
                        btnInit.setVisibility(View.VISIBLE);
                        //set hit button invisible
                        btnHit1.setVisibility(View.INVISIBLE);
                        btnStart1.setVisibility(View.INVISIBLE);
                        btnStay1.setVisibility(View.INVISIBLE);
                    } else if (dealerFourthTotal < playerFirstTotal) {
                        tvWelcome.setText("Player: " + playerFirstTotal+ " Dealer: " + dealerFourthTotal +" Player Win!");
                        score+=50;
                        Toast.makeText(mContext, "BlackJack!" + score, Toast.LENGTH_SHORT).show();
                        String txtScore = Integer.toString(score);
                        SharedPreferences.Editor editor = prefScore.edit();
                        editor.putString("score", txtScore);
                        editor.commit();
                        Toast.makeText(mContext, "Player Score: " + txtScore, Toast.LENGTH_SHORT).show();
                        //set play again button visible
                        btnInit.setVisibility(View.VISIBLE);
                        //set hit button invisible
                        btnHit1.setVisibility(View.INVISIBLE);
                        btnStart1.setVisibility(View.INVISIBLE);
                        btnStay1.setVisibility(View.INVISIBLE);

                    } else {
                        tvWelcome.setText("Player: " + playerFirstTotal+ " Dealer: " + dealerFourthTotal +" Dealer Win!");
                        score-=50;
                        Toast.makeText(mContext, "BlackJack!" + score, Toast.LENGTH_SHORT).show();
                        String txtScore = Integer.toString(score);
                        SharedPreferences.Editor editor = prefScore.edit();
                        editor.putString("score", txtScore);
                        editor.commit();
                        Toast.makeText(mContext, "Player Score: " + txtScore, Toast.LENGTH_SHORT).show();
                        //set play again button visible
                        btnInit.setVisibility(View.VISIBLE);
                        //set hit button invisible
                        btnHit1.setVisibility(View.INVISIBLE);
                        btnStart1.setVisibility(View.INVISIBLE);
                        btnStay1.setVisibility(View.INVISIBLE);

                    }
                } else if (click_times == 1 && dealerClick_time == 1) {

                    int dCard3= drawImage(cards.get(7),dealer3);
                    dealerSecondTotal = dealerFirstTotal + dCard3;
                    dealer3.setVisibility(View.VISIBLE);
                    //set hit button invisible
                    btnHit1.setVisibility(View.INVISIBLE);
                    if (dealerSecondTotal == 21) {
                        tvWelcome.setText("Black Jack! Dealer Win!!");
                        score-=50;
                        Toast.makeText(mContext, "BlackJack!" + score, Toast.LENGTH_SHORT).show();
                        String txtScore = Integer.toString(score);
                        SharedPreferences.Editor editor = prefScore.edit();
                        editor.putString("score", txtScore);
                        editor.commit();
                        Toast.makeText(mContext, "Player Score: " + txtScore, Toast.LENGTH_SHORT).show();
                        //set play again button visible
                        btnInit.setVisibility(View.VISIBLE);
                        //set hit button invisible
                        btnHit1.setVisibility(View.INVISIBLE);
                        btnStart1.setVisibility(View.INVISIBLE);
                        btnStay1.setVisibility(View.INVISIBLE);
                    } else if (dealerSecondTotal > 21) {
                        score+=50;
                        tvWelcome.setText("Dealer has: " + dealerSecondTotal+" Busted! Score: "+ score);
                        String txtScore = Integer.toString(score);
                        SharedPreferences.Editor editor = prefScore.edit();
                        editor.putString("score", txtScore);
                        editor.commit();
                        Toast.makeText(mContext, "Player Score: " + txtScore, Toast.LENGTH_SHORT).show();
                        //set play again button visible
                        btnInit.setVisibility(View.VISIBLE);
                        //set hit button invisible
                        btnHit1.setVisibility(View.INVISIBLE);
                        btnStart1.setVisibility(View.INVISIBLE);
                        btnStay1.setVisibility(View.INVISIBLE);
                    } else if (dealerSecondTotal > 17 && dealerSecondTotal > playerFirstTotal) {
                        score-=50;
                        tvWelcome.setText("Dealer has: " + dealerSecondTotal+" Dealer Win! Score: "+ score);
                        String txtScore = Integer.toString(score);
                        SharedPreferences.Editor editor = prefScore.edit();
                        editor.putString("score", txtScore);
                        editor.commit();
                        Toast.makeText(mContext, "Player Score: " + txtScore, Toast.LENGTH_SHORT).show();
                        //set play again button visible
                        btnInit.setVisibility(View.VISIBLE);
                        //set hit button invisible
                        btnHit1.setVisibility(View.INVISIBLE);
                        btnStart1.setVisibility(View.INVISIBLE);
                        btnStay1.setVisibility(View.INVISIBLE);
                    } else {
                        tvWelcome.setText("Player: " + playerFirstTotal+ " Dealer: " + dealerSecondTotal +" Please Choose Stay or Hit");

                    }
                }
                /*if (click_times == 1 && dealerClick_time==2) {
                    int dCard3= drawImage(cards.get(7),dealer3);
                    dealerSecondTotal = dealerFirstTotal + dCard3;
                    dealer3.setVisibility(View.VISIBLE);
                    if (dealerSecondTotal == 21) {
                        tvWelcome.setText("Black Jack! Dealer Win!!");
                        score-=50;
                        Toast.makeText(mContext, "BlackJack!" + score, Toast.LENGTH_SHORT).show();
                        String txtScore = Integer.toString(score);
                        SharedPreferences.Editor editor = prefScore.edit();
                        editor.putString("score", txtScore);
                        editor.commit();
                        Toast.makeText(mContext, "Player Score: " + txtScore, Toast.LENGTH_SHORT).show();
                        //set play again button visible
                        btnInit.setVisibility(View.VISIBLE);
                        //set hit button invisible
                        btnHit1.setVisibility(View.INVISIBLE);
                        btnStart1.setVisibility(View.INVISIBLE);
                        btnStay1.setVisibility(View.INVISIBLE);
                    } else if (dealerSecondTotal > 21) {
                        score+=50;
                        tvWelcome.setText("Dealer has: " + dealerSecondTotal+" Busted! Score: "+ score);
                        String txtScore = Integer.toString(score);
                        SharedPreferences.Editor editor = prefScore.edit();
                        editor.putString("score", txtScore);
                        editor.commit();
                        Toast.makeText(mContext, "Player Score: " + txtScore, Toast.LENGTH_SHORT).show();
                        //set play again button visible
                        btnInit.setVisibility(View.VISIBLE);
                        //set hit button invisible
                        btnHit1.setVisibility(View.INVISIBLE);
                        btnStart1.setVisibility(View.INVISIBLE);
                        btnStay1.setVisibility(View.INVISIBLE);
                    } else if (dealerSecondTotal > playerFirstTotal) {
                        tvWelcome.setText("Player: " + playerFirstTotal+ " Dealer: " + dealerSecondTotal +" Please Choose Stay or Hit");
                    }
                    if (click_times == 2 && dealerClick_time==3) {
                        int dCard4= drawImage(cards.get(8),dealer4);
                        dealerThirdTotal = dealerSecondTotal + dCard4;
                        dealer4.setVisibility(View.VISIBLE);
                    }

                }*/


            }

        });
        /*String txtScore = Integer.toString(score);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("score", txtScore);
        editor.commit();
        Toast.makeText(getContext().getApplicationContext(),"Score Successfully stored. Go to Result page", Toast.LENGTH_SHORT).show();
*/
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // TODO: Use the ViewModel
    }
    public int drawImage(int card, ImageView cardImage) {

        switch (card) {

            case 1:
                cardImage.setImageResource(R.drawable.ace_of_clubs);
                return 1;
            case 2:
                cardImage.setImageResource(R.drawable.two_of_clubs);
                return 2;
            case 3:
                cardImage.setImageResource(R.drawable.three_of_clubs);
                return 3;
            case 4:
                cardImage.setImageResource(R.drawable.four_of_clubs);
                return 4;
            case 5:
                cardImage.setImageResource(R.drawable.five_of_clubs);
                return 5;
            case 6:
                cardImage.setImageResource(R.drawable.six_of_clubs);
                return 6;
            case 7:
                cardImage.setImageResource(R.drawable.seven_of_clubs);
                return 7;
            case 8:
                cardImage.setImageResource(R.drawable.eight_of_clubs);
                return 8;
            case 9:
                cardImage.setImageResource(R.drawable.nine_of_clubs);
                return 9;
            case 10:
                cardImage.setImageResource(R.drawable.ten_of_clubs);
                return 10;
            case 11:
                cardImage.setImageResource(R.drawable.jack_of_clubs);
                return 10;
            case 12:
                cardImage.setImageResource(R.drawable.queen_of_clubs);
                return 10;
            case 13:
                cardImage.setImageResource(R.drawable.king_of_clubs);
                return 10;
            case 14:
                cardImage.setImageResource(R.drawable.ace_of_diamonds);
                return 1;
            case 15:
                cardImage.setImageResource(R.drawable.two_of_diamonds);
                return 2;
            case 16:
                cardImage.setImageResource(R.drawable.three_of_diamonds);
                return 3;
            case 17:
                cardImage.setImageResource(R.drawable.four_of_diamonds);
                return 4;
            case 18:
                cardImage.setImageResource(R.drawable.five_of_diamonds);
                return 5;
            case 19:
                cardImage.setImageResource(R.drawable.six_of_diamonds);
                return 6;
            case 20:
                cardImage.setImageResource(R.drawable.seven_of_diamonds);
                return 7;
            case 21:
                cardImage.setImageResource(R.drawable.eight_of_diamonds);
                return 8;
            case 22:
                cardImage.setImageResource(R.drawable.nine_of_diamonds);
                return 9;
            case 23:
                cardImage.setImageResource(R.drawable.ten_of_diamonds);
                return 10;
            case 24:
                cardImage.setImageResource(R.drawable.jack_of_diamonds);
                return 10;
            case 25:
                cardImage.setImageResource(R.drawable.queen_of_diamonds);
                return 10;
            case 26:
                cardImage.setImageResource(R.drawable.king_of_diamonds);
                return 10;
            case 27:
                cardImage.setImageResource(R.drawable.ace_of_hearts);
                return 1;
            case 28:
                cardImage.setImageResource(R.drawable.two_of_hearts);
                return 2;
            case 29:
                cardImage.setImageResource(R.drawable.three_of_hearts);
                return 3;
            case 30:
                cardImage.setImageResource(R.drawable.four_of_hearts);
                return 4;
            case 31:
                cardImage.setImageResource(R.drawable.five_of_hearts);
                return 5;
            case 32:
                cardImage.setImageResource(R.drawable.six_of_hearts);
                return 6;
            case 33:
                cardImage.setImageResource(R.drawable.seven_of_hearts);
                return 7;
            case 34:
                cardImage.setImageResource(R.drawable.eight_of_hearts);
                return 8;
            case 35:
                cardImage.setImageResource(R.drawable.nine_of_hearts);
                return 9;
            case 36:
                cardImage.setImageResource(R.drawable.ten_of_hearts);
                return 10;
            case 37:
                cardImage.setImageResource(R.drawable.jack_of_hearts);
                return 10;
            case 38:
                cardImage.setImageResource(R.drawable.queen_of_hearts);
                return 10;
            case 39:
                cardImage.setImageResource(R.drawable.king_of_hearts);
                return 10;
            case 40:
                cardImage.setImageResource(R.drawable.ace_of_spades);
                return 1;
            case 41:
                cardImage.setImageResource(R.drawable.two_of_spades);
                return 2;
            case 42:
                cardImage.setImageResource(R.drawable.three_of_spades);
                return 3;
            case 43:
                cardImage.setImageResource(R.drawable.four_of_spades);
                return 4;
            case 44:
                cardImage.setImageResource(R.drawable.five_of_spades);
                return 5;
            case 45:
                cardImage.setImageResource(R.drawable.six_of_spades);
                return 6;
            case 46:
                cardImage.setImageResource(R.drawable.seven_of_spades);
                return 7;
            case 47:
                cardImage.setImageResource(R.drawable.eight_of_spades);
                return 8;
            case 48:
                cardImage.setImageResource(R.drawable.nine_of_spades);
                return 9;
            case 49:
                cardImage.setImageResource(R.drawable.ten_of_spades);
                return 10;
            case 50:
                cardImage.setImageResource(R.drawable.jack_of_spades);
                return 10;
            case 51:
                cardImage.setImageResource(R.drawable.queen_of_spades);
                return 10;
            case 52:
                cardImage.setImageResource(R.drawable.king_of_spades);
                return 10;
            default:
                System.out.println("Please choose a card");
                break;


        }
        return 0;

    }


}
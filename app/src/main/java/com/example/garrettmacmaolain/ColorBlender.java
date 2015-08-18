package com.example.garrettmacmaolain;
         /*
             --------------------example code--------------------------

             ---------------------example code--------------------------

		     -------------------example code--------------------------

        */

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.SeekBar;



public class ColorBlender extends Activity {
    final int LEFT = 0;
    final int RIGHT = 1;
    private Intent launchActivityIntent;
    View leftView;
    View rightView;
    View blendedView;
    private SeekBar colorSlider;
    private int leftColor;
    private int rightColor;
    private int blendedColor;
    //private colorSliderListener sliderListener = new colorSliderListener();
    private int leftPercent = 100;
    /* Request code for communicating with ColorPicker via Intents */
    private static final int PICK_COLOR_REQ_CODE = 1;

    /* Enable view background to be changed in onActivityResult */
    View backgroundView;



    /**
     * Overriden onCreate to start app.
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blender_activity);

        leftView = findViewById (R.id.leftColor);
        rightView =  findViewById(R.id.blendedColor);
        blendedView =  findViewById(R.id.rightColor);
        //colorSlider = (SeekBar) findViewById(R.id.imageView);
        //colorSlider.setOnSeekBarChangeListener(sliderListener);
        leftColor = getColor(leftView);
        rightColor = getColor(rightView);
        blendedColor = blendColor(0, leftColor, rightColor);
        blendedView.setBackgroundColor(blendedColor);

        String str1=getIntent().getStringExtra("First Value");
        String str2 = getIntent().getStringExtra("Second Value");

        Intent intent = getIntent();
        Bundle info = intent.getExtras();
        if (info != null) {
		/* Retrieve vals with info.getBlah(...) */
            //info.getBundle();

            /**
             * Handle when button one is clicked, handled in main.xml with onClick.
             * Intent will launch the color picker and disable the button from
             * further use.
             *
             * @param view
             */

            /*
            ----------------example code--------------------------
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                return;
            }

            String qString = extras.getString("qString");

            final TextView textView = (TextView)
                    findViewById(R.id.textView1);
            textView.setText(qString);
            ----------------example code--------------------------
            */
        }}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.color_blender, menu);
        return true;
    }
    /*
    --------------------example code--------------------------
public void onClick(View view) {
		finish();
}

@Override
public void finish() {
	Intent data = new Intent();

	EditText editText1 = (EditText) findViewById(R.id.editText1);

	String returnString = editText1.getText().toString();
	data.putExtra("returnData", returnString);

	setResult(RESULT_OK, data);
	super.finish();
}
    ---------------------example code--------------------------

    -------------------example code--------------------------

*/
/*    @Override
    Public void onStart(){
        super.onStart();
        final View leftView = (View) onActivityResult().findViewById(R.id.leftColor);
        leftView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("button_clicked","Color 1 button clicked.");
                setLeftColor(view);
            }
        });
        }
    }*/
    public void onClick(View view) {
        finish();
    }

    @Override
    public void finish() {
        Intent data = new Intent();

        View leftView = findViewById (R.id.leftColor);
        //leftView = findViewById (R.id.leftColor);
        rightView = findViewById(R.id.blendedColor);
        blendedView = findViewById(R.id.rightColor);
        //colorSlider = (SeekBar) findViewById(R.id.imageView);
        //colorSlider.setOnSeekBarChangeListener(sliderListener);
        leftColor = getColor(leftView);
        rightColor = getColor(rightView);
        blendedColor = blendColor(0, leftColor, rightColor);
        blendedView.setBackgroundColor(blendedColor);

       // Bundle returnColors = leftView.;
       // data.putExtra("returnData", returnColors);

        setResult(RESULT_OK, data);
        super.finish();
    }
    public void addColorButtonClicked(View view) {
        //* Create Intent to open ColorPicker app *//*
        Intent i = new Intent("com.example.GET_COLOR");
        startActivityForResult(i, PICK_COLOR_REQ_CODE);

        this.backgroundView = view;

        //* Disable button so chosen color cannot be overwritten *//*

        view.setEnabled(false);
    }




    public void setLeftColor(View view) {
        //launchActivityIntent = new Intent(this, colorpicker.class);
        launchActivityIntent.putExtra("CURRENT_COLOR", leftColor);
        startActivityForResult(launchActivityIntent, LEFT);
        Log.i("ColorBlenderActivity_setLeftColor", "setLeftColor finished");
    }

    private int getColor(View view) {
        ColorDrawable output = (ColorDrawable) view.getBackground();
        return output.getColor();
    }

    private int blendColor(int rightPercent, int left, int right) {
		/*
		 * Log.i("intentActivity_blendColor", "left:" + left);
		 * Log.i("intentActivity_blendColor", "right:" + right);
		 */

        int leftRed = Color.red(left);
        int leftBlue = Color.blue(left);
        int leftGreen = Color.green(left);
        int rightRed = Color.red(right);
        int rightBlue = Color.blue(right);
        int rightGreen = Color.green(right);
        leftPercent = 100 - rightPercent;


        return Color.rgb(
                (leftRed * leftPercent + rightRed * rightPercent) / 100,
                (leftGreen * leftPercent + rightGreen * rightPercent) / 100,
                (leftBlue * leftPercent + rightBlue * rightPercent) / 100);
    }


}
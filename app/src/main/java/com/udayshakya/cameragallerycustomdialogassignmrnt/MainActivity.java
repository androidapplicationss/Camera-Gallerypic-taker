package com.udayshakya.cameragallerycustomdialogassignmrnt;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    Button btnBrowsePicrure;
    ImageView imageView;
    Bitmap bmp;
    AlertDialog.Builder ab;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnBrowsePicrure=findViewById(R.id.buttonBrowsePicture);
        imageView=findViewById(R.id.imageview);

        // Listener on btnBrowsePicrure
        btnBrowsePicrure.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Creating a Dialog
                final Dialog dialog=new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.custom_dialog);
                dialog.setCanceledOnTouchOutside(false);

                // Getting of  Dialog imageButton
               ImageButton btnCamera= dialog.findViewById(R.id.imageButtonCamera);
               ImageButton btnGallery=dialog.findViewById(R.id.imageButtonGallery);

               // Listener On ImageCamera Button
               btnCamera.setOnClickListener(new View.OnClickListener()
               {
                   @Override
                   public void onClick(View v)
                   {

                       Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                       startActivityForResult(intent,101);

                       //Toast.makeText(MainActivity.this, "Image Received", Toast.LENGTH_SHORT).show();
                       dialog.dismiss();
                   }
               });

               // Listener on Gallery Button
                btnGallery.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent intent = new Intent();
                        intent.setType("image/*"); // access all image
                        intent.setAction(Intent.ACTION_GET_CONTENT);  //get image
                        startActivityForResult(intent, 102);
                        dialog.dismiss();
                    }
                });



                dialog.show();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {

        if (requestCode==101)
        {
            Bundle b= data.getExtras();
            bmp= (Bitmap) b.get("data");
            imageView.setImageBitmap(bmp);
        }else
        {
            Uri picturePath=data.getData();  // Uri class is always  used  store class path
            imageView.setImageURI(picturePath);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
//////////////////////// BackPressed
    @Override
    public void onBackPressed()
    {
        ab=new AlertDialog.Builder(MainActivity.this);
        ab.setTitle("Warning");
        ab.setIcon(R.drawable.warning);

        ab.setCancelable(false);  /// by default true
        ab.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                finish();
            }
        });
        ab.setNegativeButton("No",new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                //Toast.makeText(MainActivity.this, "No", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        ab.setMessage("Do you want to Exit?");
        ab.show();
       // super.onBackPressed();
    }
}

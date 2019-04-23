package com.google.codelabs.mdc.java.jionow;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class CropGalleryImage extends Activity{

    public final static String chosenevent = "Event";
    public final static String mappedreceipt = "receipt";
    private static String chosen_event;
    private static Map<String, Object> receiptmap;
    private static final int REQUEST_WRITE_PERMISSION = 786;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static Context currentcontext;
    private Double subtotal = 0.00;
    private Double gst = 0.00;
    private Double servicecharge = 0.00;
    private Double total = 0.00;
    Button pickimage;
    Button identify;
    ImageView imageview;
    Uri image;
    Bitmap bitmapimage;
    String processedreceipt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receiptview);

        Intent intent = getIntent();
        chosen_event =  intent.getStringExtra(DialogActivity.event);
        Log.d("chosen",chosen_event);

        imageview = findViewById(R.id.imageview);
        pickimage = findViewById(R.id.pickimage);
        identify = findViewById(R.id.identify);
        pickimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //IF THE ANDROID SDK UP TO MARSMALLOW BUILD NUMBER
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //START REQUEST PERMISSION
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
                } else {
                    //ELSE BELOW START OPEN PICKER
                    CropImage.startPickImageActivity(CropGalleryImage.this);
                }
            }
        });

        identify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //IF THE ANDROID SDK UP TO MARSMALLOW BUILD NUMBER
                if (imageview.getDrawable() != null) {
                    try {
                        currentcontext = v.getContext();
                        runTextRecognition(v.getContext(), image);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    Context context = v.getContext();
                    Toast.makeText(context, "No image Selected",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //THIS IS HAPPEN WHEN USER CLICK ALLOW ON PERMISSION
            //START PICK IMAGE ACTIVITY
            CropImage.startPickImageActivity(CropGalleryImage.this);
        }
    }

    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);
            Log.i("RESPONSE getPath", imageUri.getPath());
            Log.i("RESPONSE getScheme", imageUri.getScheme());
            Log.i("RESPONSE PathSegments", imageUri.getPathSegments().toString());

            //NOW CROP IMAGE URI
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setMultiTouchEnabled(true)
                    //REQUEST COMPRESS SIZE
                    .setRequestedSize(800, 800)
                    //ASPECT RATIO, DELETE IF YOU NEED CROP ANY SIZE
                    //.setAspectRatio(1,1)
                    .start(this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Log.i("RESPONSE getUri", result.getUri().toString());
                image = result.getUri();
                try {
                    Bitmap bitmapimage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), image);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //GET CROPPED IMAGE URI AND PASS TO IMAGEVIEW
                imageview.setImageURI(result.getUri());
            }
        }
    }

    private void runTextRecognition(Context v, Uri uri) throws IOException {
        FirebaseVisionImage image = FirebaseVisionImage.fromFilePath(v , uri);
        FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance()
                .getOnDeviceTextRecognizer();
        detector.processImage(image)
                .addOnSuccessListener(
                        new OnSuccessListener<FirebaseVisionText>() {
                            @Override
                            public void onSuccess(FirebaseVisionText texts) {
                                processTextRecognitionResult(texts);
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Task failed with an exception
                                e.printStackTrace();
                            }
                        });
    }

    private void processTextRecognitionResult(FirebaseVisionText texts) {
        List<FirebaseVisionText.TextBlock> blocks = texts.getTextBlocks();
        //Log.d("TAG",String.valueOf(texts.getText()));
        processedreceipt = String.valueOf(texts.getText());
        Log.d("all",String.valueOf(processedreceipt));
        processString(processedreceipt);
        if (blocks.size() == 0) {
            Log.d("TAG", "No text found");
            return;
        }

    }

    private void processString(String text){

        db.collection("GlobalEvents")
                .whereEqualTo("Name", chosen_event)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                int receiptnumber = 1;
                                for (int i = 1; i<=5; i++){
                                    String receiptname = "Receipt"+i;
                                    if (document.get(receiptname)!= null){
                                        receiptnumber += 1;
                                    }
                                }
                                Log.d("ReceiptNumber",String.valueOf(receiptnumber));
                            }
                        } else {
                            Log.d("ReceiptNumber", "Error getting documents: ", task.getException());
                        }
                    }
                });
        String lines[] = text.split("\n");
        receiptmap= new HashMap<String, Object>();

        ArrayList<String> items = new ArrayList<String>();
        ArrayList<String> quantity = new ArrayList<String>();
        ArrayList<String> values = new ArrayList<String>();

        for (int i=0; i<lines.length; i++){
            if (lines[i].contains(",")){
                String price = lines[i].replace(',','.');
                values.add(price);
            }
            else if (lines[i].contains(".")){
                values.add(lines[i]);
            }
            else{
                items.add(lines[i].substring(2));
                quantity.add(lines[i].substring(0,1));
            }
        }

        for (int i = 0; i<items.size();i++){
            subtotal += Double.valueOf(quantity.get(i))*Double.valueOf(values.get(i));
        }

        servicecharge = (double) roundAvoid(0.1*subtotal,2);
        gst = (double) roundAvoid(0.07*(subtotal+servicecharge),2);
        total = subtotal+servicecharge+gst;

        //Put into itemmap for each item in receipt
        for (int j = 0; j<items.size(); j++){
            Map<String, Object> itemmap= new HashMap<String, Object>();
            itemmap.put("Price",values.get(j));
            itemmap.put("Quantity", quantity.get(j));
            receiptmap.put(String.valueOf(items.get(j)), itemmap);
        }
        receiptmap.put("Subtotal", subtotal);
        receiptmap.put("ServiceCharge", servicecharge);
        receiptmap.put("GST", gst);
        receiptmap.put("Total", total);

        //For debugging processed items from receipt
        Log.d("TAG","Passed!");
        for (int j = 0; j<items.size(); j++){
            Log.d("items",String.valueOf(items.get(j)));
            Log.d("items",String.valueOf(quantity.get(j)));
            Log.d("items",String.valueOf(values.get(j)));
        }
        Log.d("items",String.valueOf(subtotal));
        Log.d("items",String.valueOf(servicecharge));
        Log.d("items",String.valueOf(gst));
        Log.d("items",String.valueOf(total));

        //For debugging map to be sent to next page
        Log.d("items",String.valueOf(receiptmap));

        Intent intent = new Intent(currentcontext, ReceiptList.class);
        intent.putExtra(chosenevent, chosen_event);
        intent.putExtra(mappedreceipt, (HashMap) receiptmap);
        currentcontext.startActivity(intent);

    }

    public static double roundAvoid(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

}


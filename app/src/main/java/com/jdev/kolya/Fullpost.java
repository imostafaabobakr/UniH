package com.jdev.kolya;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.PRDownloaderConfig;
import com.downloader.Progress;

import java.io.File;

public class Fullpost extends AppCompatActivity {

    String title,download_url,desc,type,userId,postId,date,Fpath;
    TextView titleV,dateV,typeV,descV;
    ImageView download;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullpost);
        title = getIntent().getExtras().getString("title");
        download_url = getIntent().getExtras().getString("url");
        desc = getIntent().getExtras().getString("desc");
        type = getIntent().getExtras().getString("type");
        userId = getIntent().getExtras().getString("userid");
        postId = getIntent().getExtras().getString("postid");
        date = getIntent().getExtras().getString("date");

        titleV = findViewById(R.id.titleF);
        dateV = findViewById(R.id.dateF);
        descV = findViewById(R.id.descF);
        typeV = findViewById(R.id.typeF);
        download = findViewById(R.id.downloadF);


        titleV.setText(title);
        dateV.setText(date);
        typeV.setText(type);
        descV.setText(desc);

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    download.setEnabled(false);
                    //Create folder
                    File folder = new File(Environment.getExternalStorageDirectory() + File.separator + Fullpost.this.getString(R.string.app_name));
                    if (!folder.exists())
                        folder.mkdir();

                    Fpath = folder.getPath();

                    PRDownloader.initialize(Fullpost.this);
// Enabling database for resume support even after the application is killed:
                    PRDownloaderConfig config = PRDownloaderConfig.newBuilder()
                            .setDatabaseEnabled(true)
                            .build();
                    PRDownloader.initialize(Fullpost.this, config);

// Setting timeout globally for the download network requests:
                    config = PRDownloaderConfig.newBuilder()
                            .setReadTimeout(30_000)
                            .build();
                    PRDownloader.initialize(Fullpost.this, config);
                    int downloadId = PRDownloader.download(download_url, Fpath, title + type)
                            .build()
                            .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                                @Override
                                public void onStartOrResume() {
                                    Toast.makeText(Fullpost.this, "Download started", Toast.LENGTH_LONG).show();
                                    download.setEnabled(false);

                                }
                            })
                            .setOnPauseListener(new OnPauseListener() {
                                @Override
                                public void onPause() {

                                }
                            })
                            .setOnCancelListener(new OnCancelListener() {
                                @Override
                                public void onCancel() {

                                }
                            })
                            .setOnProgressListener(new OnProgressListener() {
                                @Override
                                public void onProgress(Progress progress) {

                                }
                            })
                            .start(new OnDownloadListener() {
                                @Override
                                public void onDownloadComplete() {
                                    Toast.makeText(Fullpost.this, "Download completed", Toast.LENGTH_LONG).show();
                                    download.setEnabled(true);

                                }

                                @Override
                                public void onError(Error error) {
                                    Toast.makeText(Fullpost.this, "Download failed", Toast.LENGTH_LONG).show();
                                    download.setEnabled(true);

                                }
                            });


                } catch (Exception e) {
                    Toast.makeText(Fullpost.this, "Error occurred", Toast.LENGTH_LONG).show();
                    download.setEnabled(true);
                }

            }
        });



    }
}

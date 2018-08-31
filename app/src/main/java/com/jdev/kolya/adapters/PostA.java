package com.jdev.kolya.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jdev.kolya.Fullpost;
import com.jdev.kolya.R;
import com.jdev.kolya.lists.PostList;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Java_Dude on 8/29/2018.
 */

public class PostA extends RecyclerView.Adapter<PostA.Pholder> {

    ArrayList<PostList> arraylist;
    Context conn;
    String name, title, date, download_url, Fpath, type, userId, postId,desc;
    FirebaseAuth mAuth;
    FirebaseFirestore firestore;

    public PostA(ArrayList<PostList> arraylist) {
        this.arraylist = arraylist;
    }

    @NonNull
    @Override
    public Pholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post, parent, false);
        conn = parent.getContext();
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        return new PostA.Pholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Pholder holder, final int position) {

        //Retrive Data
        title = arraylist.get(position).getTitle();
        download_url = arraylist.get(position).getUrl();
        type = arraylist.get(position).getType();
        postId = arraylist.get(position).postid;
        userId = arraylist.get(position).getId();
        desc = arraylist.get(position).getDesc();



        try {
            long millisecond = arraylist.get(position).getTime().getTime();
            date = android.text.format.DateFormat.format("MM/dd/yyyy", new Date(millisecond)).toString();
            holder.dateP.setText(date);
        } catch (Exception e) {
        }

        holder.deleteP.setEnabled(false);
        holder.deleteP.setVisibility(View.GONE);

        //FireBase
        firestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser().getUid().equals(userId)) {
            holder.deleteP.setEnabled(true);
            holder.deleteP.setVisibility(View.VISIBLE);
        }


        //Views
        holder.downloadP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    holder.downloadP.setEnabled(false);
                    //Create folder
                    File folder = new File(Environment.getExternalStorageDirectory() + File.separator + conn.getString(R.string.app_name));
                    if (!folder.exists())
                        folder.mkdir();

                    Fpath = folder.getPath();

                    PRDownloader.initialize(conn);
// Enabling database for resume support even after the application is killed:
                    PRDownloaderConfig config = PRDownloaderConfig.newBuilder()
                            .setDatabaseEnabled(true)
                            .build();
                    PRDownloader.initialize(conn, config);

// Setting timeout globally for the download network requests:
                    config = PRDownloaderConfig.newBuilder()
                            .setReadTimeout(30_000)
                            .setConnectTimeout(30_000)
                            .build();
                    PRDownloader.initialize(conn, config);
                    int downloadId = PRDownloader.download(download_url, Fpath, title + type)
                            .build()
                            .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                                @Override
                                public void onStartOrResume() {
                                    Toast.makeText(conn, "Download started", Toast.LENGTH_LONG).show();
                                    holder.downloadP.setEnabled(false);

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
                                    Toast.makeText(conn, "Download completed", Toast.LENGTH_LONG).show();
                                    holder.downloadP.setEnabled(true);

                                }

                                @Override
                                public void onError(Error error) {
                                    Toast.makeText(conn, "Download failed", Toast.LENGTH_LONG).show();
                                    holder.downloadP.setEnabled(true);

                                }
                            });


                } catch (Exception e) {
                    Toast.makeText(conn, "Error occurred", Toast.LENGTH_LONG).show();
                    holder.downloadP.setEnabled(true);
                }

            }
        });
        holder.deleteP.setFocusableInTouchMode(false);
        holder.deleteP.setFocusable(false);
        holder.deleteP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postId = arraylist.get(position).postid;
                firestore.collection("Posts").document("kesm").collection("mada").document(postId).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            arraylist.remove(position);
                            notifyDataSetChanged();
                            Toast.makeText(conn, "Post deleted", Toast.LENGTH_LONG).show();

                        }

                        else
                            Toast.makeText(conn, "Error : " + task.getException().getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

            }
        });
        holder.ly.setFocusableInTouchMode(false);
        holder.ly.setFocusable(false);
        holder.ly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = arraylist.get(position).getTitle();
                download_url = arraylist.get(position).getUrl();
                type = arraylist.get(position).getType();
                postId = arraylist.get(position).postid;
                userId = arraylist.get(position).getId();
                desc = arraylist.get(position).getDesc();
                Intent intent = new Intent(conn, Fullpost.class);
                intent.putExtra("title",title);
                intent.putExtra("url",download_url);
                intent.putExtra("desc",desc);
                intent.putExtra("type",type);
                intent.putExtra("userid",userId);
                intent.putExtra("postid",postId);
                intent.putExtra("date",date);
                conn.startActivity(intent);
            }
        });
        holder.titleP.setText(title);

    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    class Pholder extends RecyclerView.ViewHolder {
        TextView dateP, nameP, titleP;
        ImageView deleteP, downloadP;
        CircleImageView profile_image;
        LinearLayout ly;

        public Pholder(View itemView) {
            super(itemView);
            dateP = itemView.findViewById(R.id.dateP);
            nameP = itemView.findViewById(R.id.nameP);
            titleP = itemView.findViewById(R.id.titleP);
            deleteP = itemView.findViewById(R.id.deleteP);
            downloadP = itemView.findViewById(R.id.downloadP);
            profile_image = itemView.findViewById(R.id.profile_image);
            ly = itemView.findViewById(R.id.ly);

        }
    }
}

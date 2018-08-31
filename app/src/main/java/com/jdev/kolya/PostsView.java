package com.jdev.kolya;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.jdev.kolya.adapters.PostA;
import com.jdev.kolya.adapters.SubjectsA;
import com.jdev.kolya.lists.PostList;

import java.util.ArrayList;

public class PostsView extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseFirestore firestore;
    ArrayList<PostList> arrayList;
    PostA adapter;
    FirebaseAuth mAuth;
    Boolean isload = true;
    DocumentSnapshot lastVisible;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts_view);
        firestore = FirebaseFirestore.getInstance();
mAuth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.postlist);

        arrayList = new ArrayList<>();


        adapter = new PostA(arrayList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapter);
        loaddata();

    }



        public void loaddata() {
            if (mAuth.getCurrentUser() != null) {
                Query firstQ = firestore.collection("Posts").document("kesm").collection("mada").orderBy("time", Query.Direction.DESCENDING).limit(10);
                firstQ.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        if (documentSnapshots.size() > 0) {
                            if (isload) {
                                lastVisible = documentSnapshots.getDocuments()
                                        .get(documentSnapshots.size() - 1);
                                arrayList.clear();

                            }
                            for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                if (doc.getType() == DocumentChange.Type.ADDED) {
                                    String postid = doc.getDocument().getId();
                                    PostList postList = doc.getDocument().toObject(PostList.class).withId(postid);
                                    arrayList.add(postList);
                                    adapter.notifyDataSetChanged();

                                }

                            }
                            isload = false;
                        }
                    }
                });
                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        Boolean reachbot = !recyclerView.canScrollVertically(1);
                        if (reachbot) {
                            loadmore();
                        }

                    }
                });

            }
        }

        public void loadmore() {
            if (mAuth.getCurrentUser() != null) {
                Query nextQ =firestore.collection("Posts").document("kesm").collection("mada").orderBy("time", Query.Direction.DESCENDING).startAfter(lastVisible).limit(10);
                nextQ.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        if (!documentSnapshots.isEmpty()) {
                            lastVisible = documentSnapshots.getDocuments()
                                    .get(documentSnapshots.size() - 1);

                            for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                if (doc.getType() == DocumentChange.Type.ADDED) {
                                    String postid = doc.getDocument().getId();
                                    PostList postList = doc.getDocument().toObject(PostList.class).withId(postid);
                                    arrayList.add(postList);
                                    adapter.notifyDataSetChanged();

                                }

                            }
                        }
                    }
                });
            }
        }


    }


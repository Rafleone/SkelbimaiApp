package skelbimas.lt.Category;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import skelbimas.lt.Model.Items;
import skelbimas.lt.R;
import skelbimas.lt.ViewHolder;

public class PhonesCategory extends AppCompatActivity {

    RecyclerView mRecycleerView;
    FirebaseDatabase mFirebaseDataBase;
    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phones_category);

        //Action bar
        //ActionBar actionBar = getSupportActionBar();
        //set title
        //actionBar.setTitle("Post List");

        //Recycler View
        mRecycleerView = findViewById(R.id.recyclerView);
        mRecycleerView.setHasFixedSize(true);

        //set Layout as LinearLayout
        mRecycleerView.setLayoutManager(new LinearLayoutManager(this));

        //send Query to Firebase Databe
        mFirebaseDataBase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDataBase.getReference("Data");
    }


    //search data
    private void firebaseSearch(String searchText){
        Query firebaseSearchQuery = mRef.orderByChild("title").startAt(searchText).endAt(searchText + "\uf8ff");

        FirebaseRecyclerAdapter<Items, ViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Items, ViewHolder>(
                        Items.class,
                        R.layout.row,
                        ViewHolder.class,
                        firebaseSearchQuery
                ) {
                    @Override
                    protected void populateViewHolder(ViewHolder viewHolder, Items items, int i) {

                        viewHolder.setDetails(getApplicationContext(), items.getTitle(), items.getDescription(), items.getImage());
                    }
                };

        //set adapter to recycler view
        mRecycleerView.setAdapter(firebaseRecyclerAdapter);
    }

    //load data on recycler view on start
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Items, ViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Items, ViewHolder>(
                        Items.class,
                        R.layout.row,
                        ViewHolder.class,
                        mRef
                ) {
                    @Override
                    protected void populateViewHolder(ViewHolder viewHolder, Items items, int i) {

                        viewHolder.setDetails(getApplicationContext(), items.getTitle(), items.getDescription(), items.getImage());
                    }
                };
        //set adapter to cecycler view
        mRecycleerView.setAdapter(firebaseRecyclerAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate the menu; this adds items to the action bar if it present(pateikimas, rodimas)
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                firebaseSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //filter as you type
                firebaseSearch(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //handel other actionbar item clicks here
        if (id == R.id.action_settings){
            //TODO
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

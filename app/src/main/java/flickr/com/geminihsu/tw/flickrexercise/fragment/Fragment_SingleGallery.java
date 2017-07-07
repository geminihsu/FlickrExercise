package flickr.com.geminihsu.tw.flickrexercise.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Gallery;

import flickr.com.geminihsu.tw.flickrexercise.R;
import flickr.com.geminihsu.tw.flickrexercise.adapter.GalleryItem;
import flickr.com.geminihsu.tw.flickrexercise.adapter.GalleryItemAdapter;

public class Fragment_SingleGallery extends Fragment {

    private final int ACTIONBAR_MENU_GRID_GALLERY = 0x0001;


    private Gallery gallery;
    private String[] imageUrl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_singlegallery, container, false);
        setHasOptionsMenu(true);
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        findViews();
        Bundle data=getArguments();
        if ( data !=null)
        {
            imageUrl = data.getStringArray(Fragment_PhotoGallery.BUNDLE_GALLERY_LIST);
        }


        gallery.setAdapter(new GalleryItemAdapter(getActivity(),imageUrl));

    }
    private void findViews()
    {
        gallery = (Gallery)getView().findViewById(R.id.picture);

        gallery.setSpacing(1);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem item = menu.add(Menu.NONE, ACTIONBAR_MENU_GRID_GALLERY, Menu.NONE, getString(R.string.grid_gallery));
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case ACTIONBAR_MENU_GRID_GALLERY:


                android.support.v4.app.Fragment newFragment = new Fragment_PhotoGallery();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                Bundle b = new Bundle();
                b.putStringArray(Fragment_PhotoGallery.BUNDLE_GALLERY_LIST, imageUrl);

                newFragment.setArguments(b);
                transaction.replace(R.id.fragment_container, newFragment);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);


                transaction.commit();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
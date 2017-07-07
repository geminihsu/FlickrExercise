package flickr.com.geminihsu.tw.flickrexercise.fragment;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import flickr.com.geminihsu.tw.flickrexercise.R;
import flickr.com.geminihsu.tw.flickrexercise.adapter.GalleryItem;
import flickr.com.geminihsu.tw.flickrexercise.utils.FlickrManager;
import flickr.com.geminihsu.tw.flickrexercise.utils.ImageDownloader;

/**
 * Created by TibiaZ on 12/08/2016.
 */
public class Fragment_PhotoGallery extends Fragment {


    private final int ACTIONBAR_MENU_SINGLE_GALLERY = 0x0001;

    public final static String BUNDLE_GALLERY_LIST = "gallery_list";// from

    public interface OnItemClickListener {
        void onItemClick(GalleryItem item);
    }
    // Variables or Constants

    private static final String TAG = "Fragment_PhotoGallery";
    private RecyclerView mPhotoRecyclerView;
    private List<GalleryItem> mItems = new ArrayList<>();

    private String[] data;

    public static Fragment_PhotoGallery newInstance(){
        return new Fragment_PhotoGallery();
    }

    // onCreate

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        setRetainInstance(true);
        new FetchItemTask().execute();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case ACTIONBAR_MENU_SINGLE_GALLERY:
                data = new String[mItems.size()];
                int i = 0;
                for(GalleryItem item1 : mItems)
                {
                    data[i] = item1.getUrl();
                    i++;
                }
                android.support.v4.app.Fragment newFragment = new Fragment_SingleGallery();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                Bundle b = new Bundle();
                b.putStringArray(BUNDLE_GALLERY_LIST, data);

                newFragment.setArguments(b);
                transaction.replace(R.id.fragment_container, newFragment);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);


                transaction.commit();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem item = menu.add(Menu.NONE, ACTIONBAR_MENU_SINGLE_GALLERY, Menu.NONE, getString(R.string.single_gallery));
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_photogallery, container, false);
        setHasOptionsMenu(true);
        mPhotoRecyclerView = (RecyclerView) v
                .findViewById(R.id.fragment_photo_gallery_recycler_view);
        mPhotoRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        setupAdapter();



        return v;
    }

    private void setupAdapter(){
        if (isAdded()){
            mPhotoRecyclerView.setAdapter(new PhotoAdapter(mItems ,new Fragment_PhotoGallery.OnItemClickListener() {

                @Override
                public void onItemClick(GalleryItem item) {
                    Log.e(TAG,"Click");

                }
            }));
        }
    }

    private class PhotoHolder extends RecyclerView.ViewHolder{

        private ImageView mItemImageView;

        public PhotoHolder(View itemView){
            super(itemView);

            mItemImageView = (ImageView) itemView
                    .findViewById(R.id.fragment_photo_gallery_recycler_view);
        }

        public void bindDrawable(final GalleryItem item, final OnItemClickListener listener){
            ImageDownloader imageDownLoader = new ImageDownloader(mItemImageView);
            imageDownLoader.execute(item.getUrl());
            mItemImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
            //mItemImageView.setImageDrawable(drawable);
        }
    }

    private class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder>{

        private List<GalleryItem> mGalleryItems;
        private OnItemClickListener listener;

        public PhotoAdapter(List<GalleryItem> galleryItems, OnItemClickListener listener){
            mGalleryItems = galleryItems;
            this.listener = listener;
        }

        @Override
        public PhotoHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.gallery_item, viewGroup, false);
            return new PhotoHolder(view);
        }

        @Override
        public void onBindViewHolder(PhotoHolder photoHolder, int position){
            GalleryItem galleryItem = mGalleryItems.get(position);
            //Drawable placeholder = getResources().getDrawable(R.drawable.bill_up_close);
            photoHolder.bindDrawable(galleryItem,listener);
        }

        @Override
        public int getItemCount(){
            return mGalleryItems.size();
        }
    }

    private class FetchItemTask extends AsyncTask<Void, Void, List<GalleryItem>> {

        @Override
        protected List<GalleryItem> doInBackground(Void... params) {
            return new FlickrManager().fetchItems();
        }

        @Override
        protected void onPostExecute(List<GalleryItem> items){
            mItems = items;
            setupAdapter();
        }
    }
}

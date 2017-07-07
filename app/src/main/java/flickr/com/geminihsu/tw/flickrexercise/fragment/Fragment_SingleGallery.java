package flickr.com.geminihsu.tw.flickrexercise.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Gallery;

import flickr.com.geminihsu.tw.flickrexercise.R;
import flickr.com.geminihsu.tw.flickrexercise.adapter.GalleryItemAdapter;

public class Fragment_SingleGallery extends Fragment {


    private Gallery gallery;
    private String[] imageUrl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_singlegallery, container, false);
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
}
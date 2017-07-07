package flickr.com.geminihsu.tw.flickrexercise;

import android.support.v4.app.Fragment;

import flickr.com.geminihsu.tw.flickrexercise.fragment.Fragment_PhotoGallery;

public class MainActivity extends FlickrPhotoActivity {

    @Override
    public Fragment createFragment(){
        return Fragment_PhotoGallery.newInstance();
    }
}

package flickr.com.geminihsu.tw.flickrexercise.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import flickr.com.geminihsu.tw.flickrexercise.utils.ImageDownloader;


public class GalleryItemAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private Context context;
	private String[] image_url;
	private int mPaddingInPixels;


    public GalleryItemAdapter(Context _context, String[] url) {

		this.context = _context;
		image_url=url;
    }

	public int getCount() {
		return image_url.length;
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imgView = new ImageView(context);

		imgView.setLayoutParams(new Gallery.LayoutParams(1000, 1000));
		imgView.setScaleType(ImageView.ScaleType.FIT_CENTER);
		//imageUtils.DisplayImage(image_url[position], imgView);
		ImageDownloader imageDownLoader = new ImageDownloader(imgView);
		imageDownLoader.execute(image_url[position]);
		return imgView;
	}


	

}

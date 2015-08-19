package app.com.shah.komal.popular_movies;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.net.Uri;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private final ArrayList<Movie> mMovies;
    private final int mHeight;
    private final int mWidth;

    public ImageAdapter(Context c) {

        mContext = c;
        mMovies = new ArrayList<>();

        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Point size = new Point();
        Display display = wm.getDefaultDisplay();

        display.getSize(size);
        mWidth = Math.round((size.x) / 2);
        float imageRatio = (c.getResources().getDimension(R.dimen.poster_height)) / (c.getResources().getDimension(R.dimen.poster_width));
        mHeight = Math.round(mWidth * imageRatio);
    }

    public void addAll(Collection<Movie> xs) {
        mMovies.addAll(xs);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mMovies.size();
    }

    @Override
    public Movie getItem(int position) {
        if (position < 0 || position >= mMovies.size()) {
            return null;
        }
        return mMovies.get(position);
    }

    @Override
    public long getItemId(int position) {
        Movie movie = getItem(position);
        if (movie == null) {
            return -1L;
        }

        return movie.id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = getItem(position);
        if (movie == null) {
            return null;
        }

        ImageView imageView;
        if (convertView == null) {

            Resources r = Resources.getSystem();

            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(mWidth, mHeight));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView) convertView;
        }

        Uri posterUri = movie.buildPosterUri(mContext.getString(R.string.api_poster_default_size));
        Picasso.with(mContext)
                .load(posterUri)
                .into(imageView);

        return imageView;
    }


}
package cn.edu.gdou.www.greenhouse.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import cn.edu.gdou.httpwww.greenhouse.R;
import cn.edu.gdou.www.greenhouse.ui.activity.VideoListAdapter;
import cn.edu.gdou.www.greenhouse.utils.VideoConstant;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;


public class FragmentVideo extends BaseFragment {

    ListView listView;
    VideoListAdapter mAdapter;

    @Override
    protected View initView() {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        View contentView = mInflater.inflate(R.layout.layout_video, null);

        return contentView;
    }

    @Override
    protected void initData() {
        super.initData();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        listView = (ListView) rootView.findViewById(R.id.listview);
        mAdapter = new VideoListAdapter(getActivity());
        listView.setAdapter(mAdapter);


        return rootView;
    }
    public class VideoListAdapter extends BaseAdapter {

        int[] viewtype = {0, 1, 1, 1, 0, 1, 0, 1};//1 = jcvd, 0 = textView

        Context context;
        LayoutInflater mInflater;

        public VideoListAdapter(Context context) {
            this.context = context;
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return viewtype.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //This is the point
            if (convertView != null && convertView.getTag()
                    != null && convertView.getTag() instanceof VideoHolder) {
                ((VideoHolder) convertView.getTag()).jcVideoPlayer.release();
            }
            if (getItemViewType(position) == 1) {
                VideoHolder viewHolder;
                if (convertView != null && convertView.getTag()
                        != null && convertView.getTag() instanceof VideoHolder) {
                    viewHolder = (VideoHolder) convertView.getTag();
                } else {
                    viewHolder = new VideoHolder();
                    convertView = mInflater.inflate(R.layout.item_videoview, null);
                    viewHolder.jcVideoPlayer = (JCVideoPlayerStandard) convertView.findViewById(R.id.videoplayer);
                    convertView.setTag(viewHolder);
                }

                viewHolder.jcVideoPlayer.setUp(
                        VideoConstant.videoUrls[0][position], JCVideoPlayer.SCREEN_LAYOUT_LIST,
                        VideoConstant.videoTitles[0][position]);

                Picasso.with(getActivity())
                        .load(VideoConstant.videoThumbs[0][position])
                        .into(viewHolder.jcVideoPlayer.thumbImageView);
            } else {
                TextViewHolder textViewHolder;
                if (convertView != null && convertView.getTag() != null && convertView.getTag() instanceof TextViewHolder) {
                    textViewHolder = (TextViewHolder) convertView.getTag();
                } else {
                    textViewHolder = new TextViewHolder();
                    LayoutInflater mInflater = LayoutInflater.from(context);
                    convertView = mInflater.inflate(R.layout.item_textview, null);
                    textViewHolder.textView = (TextView) convertView.findViewById(R.id.textview);
                    convertView.setTag(textViewHolder);
                }
            }
            return convertView;
        }

        /**
         * 根据位置得到对应的类型
         * @param position
         * @return
         */
        @Override
        public int getItemViewType(int position) {
            return viewtype[position];
        }

        /**
         * 得到类型的总数
         * @return
         */
        @Override
        public int getViewTypeCount() {
            return 2;
        }

        class VideoHolder {
            JCVideoPlayerStandard jcVideoPlayer;
        }

        class TextViewHolder {
            TextView textView;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}

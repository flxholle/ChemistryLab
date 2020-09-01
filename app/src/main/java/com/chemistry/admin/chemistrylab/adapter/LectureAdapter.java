package com.chemistry.admin.chemistrylab.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chemistry.admin.chemistrylab.R;
import com.chemistry.admin.chemistrylab.fragment.LectureFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Created by Admin on 10/19/2016.
 */

public class LectureAdapter extends BaseAdapter {
    private List<ItemLecture> listItemLecture;
    private final LayoutInflater inflater;
    private final Context context;

    public LectureAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        initListLecture();
    }

    private void initListLecture() {
        String lang = Locale.getDefault().getLanguage();
        List<String> availableLanguages = Arrays.asList("vi", "en");
        String langFolder;
        if (availableLanguages.contains(lang))
            langFolder = lang;
        else
            langFolder = "en";

        listItemLecture = new ArrayList<>();
        listItemLecture.add(new ItemLecture(LectureFragment.LITERATURE_DOWNLOAD_LINK + langFolder + "_Chapter1.pdf",
                context.getString(R.string.chapter_one)));
        listItemLecture.add(new ItemLecture(LectureFragment.LITERATURE_DOWNLOAD_LINK + langFolder + "_Chapter2.pdf",
                context.getString(R.string.chapter_two)));
        listItemLecture.add(new ItemLecture(LectureFragment.LITERATURE_DOWNLOAD_LINK + langFolder + "_Chapter3.pdf",
                context.getString(R.string.chapter_three)));
        listItemLecture.add(new ItemLecture(LectureFragment.LITERATURE_DOWNLOAD_LINK + langFolder + "_Chapter4.pdf",
                context.getString(R.string.chapter_four)));
        listItemLecture.add(new ItemLecture(LectureFragment.LITERATURE_DOWNLOAD_LINK + langFolder + "_Chapter5.pdf",
                context.getString(R.string.chapter_five)));
        listItemLecture.add(new ItemLecture(LectureFragment.LITERATURE_DOWNLOAD_LINK + langFolder + "_Chapter6.pdf",
                context.getString(R.string.chapter_six)));
        listItemLecture.add(new ItemLecture(LectureFragment.LITERATURE_DOWNLOAD_LINK + langFolder + "_Chapter7.pdf",
                context.getString(R.string.chapter_seven)));
        listItemLecture.add(new ItemLecture(LectureFragment.LITERATURE_DOWNLOAD_LINK + langFolder + "_Chapter8.pdf",
                context.getString(R.string.chapter_eight)));
        listItemLecture.add(new ItemLecture(LectureFragment.LITERATURE_DOWNLOAD_LINK + langFolder + "_Chapter9.pdf",
                context.getString(R.string.chapter_nine)));
        listItemLecture.add(new ItemLecture(LectureFragment.LITERATURE_DOWNLOAD_LINK + langFolder + "_Chapter10.pdf",
                context.getString(R.string.chapter_ten)));
        listItemLecture.add(new ItemLecture(LectureFragment.LITERATURE_DOWNLOAD_LINK + langFolder + "_Chapter11.pdf",
                context.getString(R.string.chapter_eleven)));
    }

    @Override
    public int getCount() {
        return listItemLecture.size();
    }

    @Override
    public ItemLecture getItem(int position) {
        return listItemLecture.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.item_lecture, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textLectureTitle = convertView.findViewById(R.id.txt_lecture_title);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textLectureTitle.setText(listItemLecture.get(position).getTitle());
        return convertView;
    }

    private static class ViewHolder{
        TextView textLectureTitle;
    }

    public static class ItemLecture{
        private final String path;
        private final String title;

        public ItemLecture(String path, String title) {
            this.path = path;
            this.title = title;
        }

        public String getPath() {
            return path;
        }

        public String getTitle() {
            return title;
        }
    }
}

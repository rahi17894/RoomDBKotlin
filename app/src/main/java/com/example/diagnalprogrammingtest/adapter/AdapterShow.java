package com.example.diagnalprogrammingtest.adapter;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.example.diagnalprogrammingtest.BR;
import com.example.diagnalprogrammingtest.R;
import com.example.diagnalprogrammingtest.databinding.AdapterShowListItemBinding;
import com.example.diagnalprogrammingtest.model.Content;
import com.example.diagnalprogrammingtest.utils.DataUtils;
import java.util.ArrayList;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdapterShow extends RecyclerView.Adapter<AdapterShow.AdapterShowViewHolder>
{
    private Context mContext;
    private ArrayList<Content> contents;
    private Integer value;
    private int found_position;
    private String search_string="";

    public AdapterShow(Context context, ArrayList<Content> content_data)
    {
        this.mContext = context;
        this.contents = content_data;

    }

    @NonNull
    @Override
    public AdapterShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        AdapterShowListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
            R.layout.adapter_show_list_item, parent, false);
        return new AdapterShowViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterShowViewHolder holder, int position)
    {
        Content dataModel = contents.get(position);
        holder.bind(dataModel);

        holder.itemRowBinding.tvShowName.setText(contents.get(position).getName());

        String desc = contents.get(position).getName();
        SpannableStringBuilder sb = new SpannableStringBuilder(desc);
        Pattern word = Pattern.compile(search_string.toLowerCase());
        Matcher match = word.matcher(desc.toLowerCase());

        while (match.find()) {
            ForegroundColorSpan fcs = new ForegroundColorSpan(
                    ContextCompat.getColor(mContext, R.color.colorYellow));
            sb.setSpan(fcs, match.start(), match.end(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        }
        holder.itemRowBinding.tvShowName.setText(sb);

        Set<String> keys = DataUtils.getImage().keySet();
        for(String key: keys){
            if (contents.size()>0){
                if (contents.get(position).getPosterImage().split("\\.")[0].equalsIgnoreCase(key)){
                    value=DataUtils.getImage().get(key);
                    found_position=position;
                }
                else {
                    if (position!=found_position){
                        value=R.drawable.placeholder_for_missing_posters;
                    }

                }
            }

        }

        holder.itemRowBinding.ivPosterImage.setImageResource(value);

    }

    @Override
    public int getItemCount()
    {
        return contents.size();
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public int getItemViewType(int position)
    {
        return position;
    }

    class AdapterShowViewHolder extends RecyclerView.ViewHolder
    {

        AdapterShowListItemBinding itemRowBinding;

        AdapterShowViewHolder(AdapterShowListItemBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }

        void bind(Object obj) {
            itemRowBinding.setVariable(BR.content, obj);
            itemRowBinding.executePendingBindings();
        }


    }

    public void filterList(ArrayList<Content> filteredList,String search_strings) {
        contents = filteredList;
        search_string=search_strings;
        notifyDataSetChanged();
    }
}

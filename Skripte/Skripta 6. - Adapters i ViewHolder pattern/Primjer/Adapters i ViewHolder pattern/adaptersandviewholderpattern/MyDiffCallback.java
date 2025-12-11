package hr.fipu.adaptersandviewholderpattern;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

public class MyDiffCallback extends DiffUtil.Callback {
    private final List<String> oldList;
    private final List<String> newList;
    public MyDiffCallback(List<String> oldList, List<String> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }
    @Override
    public int getOldListSize() {
        return oldList.size();
    }
    @Override
    public int getNewListSize() {
        return newList.size();
    }
    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return areContentsTheSame(oldItemPosition, newItemPosition);
    }
    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }
}

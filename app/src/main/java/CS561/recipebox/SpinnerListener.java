package CS561.recipebox;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;

import java.util.List;

public interface SpinnerListener {
    void onItemsSelected(List<KeyPairBoolData> items);
}

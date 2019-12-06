package CS561.recipebox.Spinner;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;

import java.util.List;

public interface SpinnerListener {
    void onItemsSelected(List<KeyPairBoolData> items);
}

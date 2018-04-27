package sy.soya.lear.ui.helpers;

import android.databinding.Observable;
import android.databinding.PropertyChangeRegistry;

public class BaseObservable implements Observable {
    protected static final PropertyChangeRegistry registry = new PropertyChangeRegistry();

    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        registry.add(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        registry.remove(callback);
    }
}

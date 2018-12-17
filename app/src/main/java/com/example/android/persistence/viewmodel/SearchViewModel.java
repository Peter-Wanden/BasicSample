package com.example.android.persistence.viewmodel;

import android.app.Application;

import com.example.android.persistence.DataRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class SearchViewModel extends AndroidViewModel {

    private MutableLiveData<String> mOriginatingClass = new MutableLiveData<>();
    private MutableLiveData<String> mQuery = new MutableLiveData<>();

    public SearchViewModel(@NonNull Application application) {
        super(application);
    }

    public void setOriginatingClass(String originatingClass) {
        mOriginatingClass.setValue(originatingClass);
    }
    public MutableLiveData<String> getOriginatingClass() {
        return mOriginatingClass;
    }

    public void setQuery(String query) {
        mQuery.setValue(query);
    }
    public MutableLiveData<String> getQuery() {
        return mQuery;
    }
}

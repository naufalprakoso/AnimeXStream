package net.xblacky.animexstream.ui.main.favourites

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import net.xblacky.animexstream.utils.model.FavouriteModel
import net.xblacky.animexstream.utils.realm.InitializeRealm

class FavouriteViewModel @ViewModelInject constructor() : ViewModel() {

    private lateinit var result: RealmResults<FavouriteModel>
    private val realm = Realm.getInstance(InitializeRealm.getConfig())
    private val _favouriteLists: MutableLiveData<ArrayList<FavouriteModel>> =
        MutableLiveData(ArrayList())
    var favouriteList: LiveData<ArrayList<FavouriteModel>> = _favouriteLists

    init {
        favouriteListListener()
    }

    private fun favouriteListListener() {
        result = realm.where(FavouriteModel::class.java).sort("insertionTime", Sort.DESCENDING).findAll()
        _favouriteLists.value = realm.copyFromRealm(result) as ArrayList<FavouriteModel>?
        result.addChangeListener { newList ->
            _favouriteLists.value = realm.copyFromRealm(newList) as ArrayList<FavouriteModel>?
        }

    }
}


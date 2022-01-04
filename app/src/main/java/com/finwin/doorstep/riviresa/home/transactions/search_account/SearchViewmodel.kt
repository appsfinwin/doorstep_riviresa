package com.finwin.doorstep.riviresa.home.transactions.search_account

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.view.View
import android.widget.AdapterView
import androidx.databinding.*
import androidx.databinding.Observable.OnPropertyChangedCallback
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import cn.pedant.SweetAlert.SweetAlertDialog
import com.finwin.doorstep.riviresa.retrofit.ApiInterface
import com.finwin.doorstep.riviresa.retrofit.RetrofitClient
import com.finwin.doorstep.riviresa.utils.Services
import com.finwin.doorstep.riviresa.login.pojo.getMasters.Data
import com.finwin.doorstep.riviresa.utils.DataHolder
import  com.finwin.doorstep.riviresa.BR
import com.finwin.doorstep.riviresa.utils.Constants
import io.reactivex.disposables.CompositeDisposable
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject
import java.util.HashMap

class SearchViewmodel(application: Application) : AndroidViewModel(application), Observable {
    var sharedPreferences: SharedPreferences = application.getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE)
    var branchList = ObservableArrayList<Any>()
    var branchListData = ObservableArrayList<Data>()
    lateinit var repository: SearchRepository

    var accountType = ObservableArrayList<Any>()
    var accountTypeData = ObservableArrayList<Data>()
    private val registry = PropertyChangeRegistry()
    lateinit var mAction:MutableLiveData<SearchAction>
    lateinit var compositeDisposable:CompositeDisposable
    lateinit var branchId: String
    lateinit var accountTypeId: String
    var apiInterface = RetrofitClient().getApi()!!
    lateinit var name:ObservableField<String>
    init {
        name= ObservableField("")
        mAction= MutableLiveData()
        compositeDisposable= CompositeDisposable()
        repository= SearchRepository(compositeDisposable,mAction)
        repository.mAction=mAction
        branchList.add("--Select Branch--")
        branchListData.add(Data("", "", ""))

        accountType.add("--Select Account Type--")
        accountTypeData.add(Data("", "", ""))

        for (branch in DataHolder.branchdetails) {
            branchList.add(branch.NAME)
            branchListData.add(branch)
        }

        for (account in DataHolder.accountType) {
            accountType.add(account.NAME)
            accountTypeData.add(account)
        }
    }

    private var selectedBranch = 0
    private var selectedAccountType = 0

    @Bindable
    fun getSelectedBranch(): Int {
        return selectedBranch
    }

    fun setSelectedBranch(selectedDistrict: Int) {
        this.selectedBranch = selectedDistrict
        registry.notifyChange(this, BR.selectedBranch)
    }

    fun onSelectedBranch(parent: AdapterView<*>?, view: View?, position: Int, id: Long
    ) {

            branchId = branchListData.get(position).ID.toString()

    }

    @Bindable
    fun getSelectedAccountType(): Int {
        return selectedAccountType
    }

    fun setSelectedAccountType(selectedDistrict: Int) {
        this.selectedAccountType = selectedDistrict
        registry.notifyChange(this, BR.selectedAccountType)
    }

    fun onSelectedAccountType(parent: AdapterView<*>?, view: View?, position: Int, id: Long
    ) {

            accountTypeId = accountTypeData.get(position).ID.toString()

    }


    override fun addOnPropertyChangedCallback(callback: OnPropertyChangedCallback?) {
        registry.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: OnPropertyChangedCallback?) {
        registry.remove(callback)
    }

    private fun search() {

        val jsonParams: MutableMap<String?, Any?> = HashMap()
       // jsonParams["agent_id"] =sharedPreferences.getString(Constants.AGENT_ID, "")
        jsonParams["agent_id"] = ""
        jsonParams["branch_id"] = branchId
        jsonParams["name"] = name.get()
        jsonParams["acc_type"] =accountTypeId
        jsonParams["acc_cat"] = ""

        val body = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            JSONObject(jsonParams).toString()
        )
        repository.searchAccount(apiInterface, body)
    }
    var loading: SweetAlertDialog? = null

    fun initLoading(context: Context?) {
        loading = Services.showProgressDialog(context)
    }

    fun cancelLoading() {
        if (loading != null) {
            loading!!.cancel()
            loading = null
        }
    }

    fun clickSearch(view: View?)
    {
        initLoading(view?.context)
        search()

    }

    fun clickCancel(view: View?)
    {
        mAction.value= SearchAction(SearchAction.CLICK_CANCEL)

    }
}
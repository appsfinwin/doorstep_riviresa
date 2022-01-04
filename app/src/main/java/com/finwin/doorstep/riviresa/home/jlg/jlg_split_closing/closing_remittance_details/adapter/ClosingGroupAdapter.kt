package com.finwin.doorstep.riviresa.home.jlg.jlg_split_closing.closing_remittance_details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.finwin.doorstep.riviresa.R
import com.finwin.doorstep.riviresa.databinding.LayoutRowClosingGroupBinding
import com.finwin.doorstep.riviresa.home.jlg.JlgAction
import com.finwin.doorstep.riviresa.home.jlg.split_transaction.pojo.Dat
import java.util.*

class ClosingGroupAdapter: RecyclerView.Adapter<ClosingGroupAdapter.ViewHolder>() {

    var dataList: List<Dat> = Collections.emptyList()
    var mAction: MutableLiveData<JlgAction> = MutableLiveData()

    var dataListLiveData: MutableLiveData<List<Dat>> = MutableLiveData()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClosingGroupAdapter.ViewHolder {
        val binding: LayoutRowClosingGroupBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_row_closing_group,
                parent,
                false
            )
       return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ClosingGroupAdapter.ViewHolder, position: Int) {
       holder.setBindData(dataList,position,dataListLiveData)
    }

    fun setData(dataList: List<Dat>)
    {
        this.dataList= Collections.emptyList()
        this.dataList= dataList
        dataListLiveData.value= dataList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
       return dataList.size
    }

    class ViewHolder(val binding: LayoutRowClosingGroupBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setBindData(dat: List<Dat>, position: Int, dataListLiveData: MutableLiveData<List<Dat>>) {

            binding.apply {
                binding.viewModel= RowGroupViewModel(dat,dataListLiveData,position)
            }

        }
    }
}
package com.finwin.doorstep.riviresa.home.transactions.cash_transfer

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import cn.pedant.SweetAlert.SweetAlertDialog
import com.finwin.doorstep.riviresa.R
import com.finwin.doorstep.riviresa.databinding.CashTransferFragmentBinding
import com.finwin.doorstep.riviresa.home.transactions.search_account.SearchActivity
import com.finwin.doorstep.riviresa.print_reciept.ReceiptActivity
import com.finwin.doorstep.riviresa.utils.Constants

class CashTransferFragment : Fragment() {

    companion object {
        fun newInstance() = CashTransferFragment()
    }

    private lateinit var viewModel: CashTransferViewModel
    private lateinit var binding: CashTransferFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= DataBindingUtil.inflate(inflater,R.layout.cash__transfer_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CashTransferViewModel::class.java)
        binding.viewModel=viewModel
        // TODO: Use the ViewModel

        viewModel.mAction.observe(viewLifecycleOwner, Observer {
            viewModel.cancelLoading()
            when(it.action)
            {

                CashTransferAction.API_ERROR->{}
                CashTransferAction.CASH_TRANSFER_SUCCESS->{
                    var intent = Intent(activity, ReceiptActivity::class.java)
                    intent.putExtra(Constants.FROM, Constants.CASH_DEPOSIT)

                    it.cashTransferResponse.let { response ->

                        if (response != null) {
                            var intent = Intent(activity, ReceiptActivity::class.java)
                            intent.putExtra(Constants.FROM, Constants.CASH_DEPOSIT)


                            intent.putExtra(
                                Constants.TRANSACTION_ID,
                                response.receipt.data.TRAN_ID
                            )

                        intent.putExtra(
                            Constants.OLD_BALANCE,
                            response.receipt.data.OLD_BALANCE
                        )
                        intent.putExtra(
                            Constants.DEPOSIT_AMOUNT,
                            response.receipt.data.WITHDRAWAL_AMOUNT
                        )
                        intent.putExtra(
                            Constants.CURRENT_BALANCE,
                            response.receipt.data.CURRENT_BALANCE
                        )
                        intent.putExtra(
                            Constants.DEPOSIT_DATE,
                            response.receipt.data.TRANSFER_DATE
                        )
                        intent.putExtra(
                            Constants.PREVIOUS_BALANCE,
                            response.receipt.data.OLD_BALANCE
                        )
                        intent.putExtra(
                            Constants.ACCOUNT_NUMBER,
                            response.receipt.data.ACC_NO
                        )
                        intent.putExtra(Constants.NAME, response.receipt.data.NAME)
                        intent.putExtra(
                            Constants.MOBILE,
                            response.receipt.data.MOBILE
                        )
                        startActivityForResult(intent, Constants.INTENT_RECIEPT_FROM_CASH_DEPOSIT)
                    }
                    }


                }
                CashTransferAction.OTP_GENERATE_SUCCESS->{
                    viewModel.otpId.set(it.otpGenerateResponse?.otp?.otp_id)
                }
                CashTransferAction.CLICK_SEARCH_DEBIT_ACCOUNT -> {
                    var intent: Intent = Intent(activity, SearchActivity::class.java)
                    startActivityForResult(
                        intent,
                        Constants.INTENT_SEARCH_ACCOUNT_FOR_DEBIT_ACCOUNT
                    )
                }
                CashTransferAction.CLICK_SEARCH_CREDIT_ACCOUNT -> {
                    var intent: Intent = Intent(activity, SearchActivity::class.java)
                    startActivityForResult(
                        intent,
                        Constants.INTENT_SEARCH_ACCOUNT_FOR_CREDIT_ACCOUNT
                    )
                }
                CashTransferAction.GET_DEBIT_ACCOUNT_HOLDER_SUCCESS -> {
                    viewModel.creditLayoutVisibility.set(View.VISIBLE)
                    it.getAccountHolderResponse?.let { it1 ->
                        viewModel.setDebitAccountHolderData(
                            it1.account)
                    }
                }
                CashTransferAction.GET_CREDIT_ACCOUNT_HOLDER_SUCCESS -> {
                    viewModel.creditLayoutVisibility.set(View.VISIBLE)
                    it.getAccountHolderResponse?.let { response ->
                        viewModel.setCreditAccountHolderData(
                            response.account)
                    }
                    viewModel.amountLayoutVisibility.set(View.VISIBLE)
                }
//                CashTransferAction.CLICK_DEPOSIT -> {
//                    //showDialog()
//                }

                CashTransferAction.API_ERROR -> {
                    val customView: View =
                        LayoutInflater.from(context).inflate(R.layout.layout_error_layout, null)
                    val tv_error = customView.findViewById<TextView>(R.id.tv_error)
                    tv_error.setText(it.error)
                    SweetAlertDialog(context, SweetAlertDialog.BUTTON_NEGATIVE)
                        .setTitleText("ERROR")
                        .setCustomView(customView)
                        .show()
                }
                CashTransferAction.OTP_GENERATE_SUCCESS->
                {
                    viewModel.otpId.set(it.otpGenerateResponse?.otp?.otp_id)
                    viewModel.otpLayoutVisibility.set(View.VISIBLE)
                }
            }

        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.INTENT_SEARCH_ACCOUNT_FOR_CREDIT_ACCOUNT) {
            if (data != null) {
                var accountNumber: String? = data.getStringExtra("account_number")
                viewModel.creditAccountNumber.set(accountNumber)
            } else {
                viewModel.creditAccountNumber.set("")
            }
        } else if (requestCode == Constants.INTENT_SEARCH_ACCOUNT_FOR_DEBIT_ACCOUNT) {
            if (data != null) {
                var accountNumber: String? = data.getStringExtra("account_number")
                viewModel.debitAccountNumber.set(accountNumber)
            } else {
                viewModel.debitAccountNumber.set("")
            }
        }
    }

}
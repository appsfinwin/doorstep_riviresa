package com.finwin.doorstep.riviresa.print_reciept.loan_recipt

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.finwin.doorstep.riviresa.R
import com.finwin.doorstep.riviresa.databinding.ActivityLoanReceiptBinding
import com.finwin.doorstep.riviresa.logout_listner.BaseActivity
import com.finwin.doorstep.riviresa.utils.Constants

class LoanReceiptActivity : BaseActivity() {

    lateinit var viewModel: LoanReceiptViewModel
    lateinit var binding: ActivityLoanReceiptBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_loan_receipt)
        viewModel = ViewModelProvider(this)[LoanReceiptViewModel::class.java]
        binding.viewmodel = viewModel

        viewModel.bankName.set(application.resources.getString(R.string.bank_name))



            viewModel.setReceipt(
                intent.getStringExtra(Constants.TRANSACTION_DATE).toString(),
                intent.getStringExtra(Constants.TRANSACTION_TIME).toString(),
                intent.getStringExtra(Constants.LOAN_ACCOUNT_NUMBER).toString(),
                intent.getStringExtra(Constants.LOAN_CUSTOMER_NAME).toString(),
                intent.getStringExtra(Constants.REMITTANCE_AMOUNT).toString(),

            )


    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
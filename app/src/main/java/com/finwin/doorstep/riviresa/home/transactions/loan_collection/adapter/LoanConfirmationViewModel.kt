package com.finwin.doorstep.riviresa.home.transactions.loan_collection.adapter

import androidx.databinding.BaseObservable
import com.finwin.doorstep.riviresa.databinding.LayoutLoanCollectionConfirmationBinding

class LoanConfirmationViewModel: BaseObservable() {
    fun setConfirmData(
        binding: LayoutLoanCollectionConfirmationBinding,
        accountNumber: String?,
        amount: String,
        customerName: String?,
        customerId: String?
    ) {

        binding.tvAccountNumber.text=accountNumber
        binding.tvMobile.text=customerId
        binding.tvName.text=customerName
        binding.tvDepositAmount.text=amount

    }

}
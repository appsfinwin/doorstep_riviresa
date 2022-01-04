package com.finwin.doorstep.riviresa.print_reciept

import android.view.View
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.finwin.doorstep.riviresa.R
import com.finwin.doorstep.riviresa.utils.Constants
import com.finwin.doorstep.riviresa.utils.sunmi.SunmiPrintHelper
import com.softland.palmtecandro.palmtecandro

class RecieptViewmodel : ViewModel() {

    var dateAndTime: ObservableField<String> = ObservableField("")
    var accountNumber: ObservableField<String> = ObservableField("")
    var transactionNumber: ObservableField<String> = ObservableField("")
    var name: ObservableField<String> = ObservableField("")
    var mobile: ObservableField<String> = ObservableField("")
    var previousBalance: ObservableField<String> = ObservableField("")
    var depositAmount: ObservableField<String> = ObservableField("")
    var currentBalance: ObservableField<String> = ObservableField("")
    var from: ObservableField<String> = ObservableField("")
    var balanceVisibility: ObservableField<Int> = ObservableField(View.GONE)
    var BILL_CASH = ""
    var bankName= ObservableField("")

    init {
        if (android.os.Build.MODEL.contains("PALMTEC")) {
            palmtecandro.jnidevOpen(115200)
        }
    }

    var mAction: MutableLiveData<String> = MutableLiveData()
    fun setReceiptCashDeposit(
        dateTime: String?,
        accountNumber: String,
        transactionNumber: String,
        name: String,
        mobile: String,
        previousBalance: String,
        depositAmount: String,
        currentBalance: String

    ) {
        this.dateAndTime.set(dateTime)
        this.accountNumber.set(accountNumber)
        this.transactionNumber.set(transactionNumber)
        this.name.set(name)
        this.mobile.set(mobile)
        this.previousBalance.set(previousBalance)
        this.depositAmount.set(depositAmount)
        this.currentBalance.set(currentBalance)
    }

    fun setReceiptBalanceEnquiry(
        dateTime: String?,
        accountNumber: String,
        name: String,
        mobile: String,
        currentBalance: String

    ) {
        this.dateAndTime.set(dateTime)
        this.accountNumber.set(accountNumber)
        this.name.set(name)
        this.mobile.set(mobile)
        this.currentBalance.set(currentBalance)
    }

    fun clickPrint(view: View) {
//        mAction.value="print"

        if (android.os.Build.MODEL.contains("PALMTEC")) {
            if (from.get().equals(Constants.CASH_DEPOSIT)) {
                printCash()
            }else if (from.get().equals(Constants.BALANCE_ENQUIRY))
            {
                printBalance()
            }
        } else  if (android.os.Build.MODEL.contains("V2")){

            if (from.get().equals(Constants.CASH_DEPOSIT)) {
                printCashV2()
            }else if (from.get().equals(Constants.BALANCE_ENQUIRY))
            {
                printBalanceV2()
            }

        }

        else  {
            Toast.makeText(view.context, "Printing not supported in this device", Toast.LENGTH_LONG)
                .show()
        }
    }

    override fun onCleared() {
        super.onCleared()
        mAction.value = "default"
        if (android.os.Build.MODEL.contains("PALMTEC")) {
            palmtecandro.jnidevClose()
        }

    }


    fun printCash() {
        try {

            BILL_CASH = "\n       "+ R.string.bank_name+"          \n\n";
            BILL_CASH = BILL_CASH + "Date             : " + dateAndTime.get() + "\n";
            BILL_CASH = BILL_CASH + "Tran ID          : " + transactionNumber.get() + "\n";
            BILL_CASH = BILL_CASH + "Account Number   : " + accountNumber.get() + "\n";
            BILL_CASH = BILL_CASH + "Name             : " + name.get() + "\n";
            BILL_CASH = BILL_CASH + "Mobile Number    : " + mobile.get() + "\n";
            BILL_CASH = BILL_CASH + "------------------------------";
            BILL_CASH = BILL_CASH + "\n\n";
            BILL_CASH = BILL_CASH + "Opening Balance  : " + previousBalance.get() + "\n";
            BILL_CASH = BILL_CASH + "Deposit Amount   : " + depositAmount.get() + "\n";
            BILL_CASH = BILL_CASH + "Current Balance  : " + currentBalance.get() + "\n\n";
            BILL_CASH = BILL_CASH + "Thank you for Banking with us...\n";
            BILL_CASH = BILL_CASH + "------------------------------\n\n\n";

            Print(BILL_CASH)

        } catch (e: NullPointerException) {
        }
    }

    fun printCashV2() {
        try {

            BILL_CASH = "\n                "+bankName.get()+"                \n\n";
            BILL_CASH = BILL_CASH + "Date             : " + dateAndTime.get() + "\n";
            BILL_CASH = BILL_CASH + "Tran ID          : " + transactionNumber.get() + "\n";
            BILL_CASH = BILL_CASH + "Account Number   : " + accountNumber.get() + "\n";
            BILL_CASH = BILL_CASH + "Name             : " + name.get() + "\n";
            BILL_CASH = BILL_CASH + "Mobile Number    : " + mobile.get() + "\n";
            BILL_CASH = BILL_CASH + "------------------------------";
            BILL_CASH = BILL_CASH + "\n\n";
            BILL_CASH = BILL_CASH + "Opening Balance  : " + previousBalance.get() + "\n";
            BILL_CASH = BILL_CASH + "Deposit Amount   : " + depositAmount.get() + "\n";
            BILL_CASH = BILL_CASH + "Current Balance  : " + currentBalance.get() + "\n\n";
            BILL_CASH = BILL_CASH + "Thank you for Banking with us...\n";
            BILL_CASH = BILL_CASH + "------------------------------\n\n\n";



            SunmiPrintHelper.getInstance().printText(BILL_CASH, 18F, false, false)

        } catch (e: NullPointerException) {
        }
    }



    fun printBalance() {
        try {

            BILL_CASH = "\n       "+ "R.string.bank_name"+"          \n\n";
            BILL_CASH = BILL_CASH + "Date             : " + dateAndTime.get() + "\n";
            BILL_CASH = BILL_CASH + "Account Number   : " + accountNumber.get() + "\n";
            BILL_CASH = BILL_CASH + "" +
                    "Name             : " + name.get() + "\n";
            BILL_CASH = BILL_CASH + "Mobile Number    : " + mobile.get() + "\n";
            BILL_CASH = BILL_CASH + "------------------------------";
            BILL_CASH = BILL_CASH + "\n\n";
            BILL_CASH = BILL_CASH + "Current Balance  : " + currentBalance.get() + "\n\n";
            BILL_CASH = BILL_CASH + "Thank you for Banking with us...\n";
            BILL_CASH = BILL_CASH + "------------------------------\n\n\n";

            Print(BILL_CASH)

        } catch (e: NullPointerException) {
        }
    }

    fun printBalanceV2() {
        try {

            BILL_CASH = "\n                "+bankName.get()+"                \n\n";
            BILL_CASH = BILL_CASH + "Date             : " + dateAndTime.get() + "\n";
            BILL_CASH = BILL_CASH + "Account Number   : " + accountNumber.get() + "\n";
            BILL_CASH = BILL_CASH + "" +
                    "Name             : " + name.get() + "\n";
            BILL_CASH = BILL_CASH + "Mobile Number    : " + mobile.get() + "\n";
            BILL_CASH = BILL_CASH + "------------------------------";
            BILL_CASH = BILL_CASH + "\n\n";
            BILL_CASH = BILL_CASH + "Current Balance  : " + currentBalance.get() + "\n\n";
            BILL_CASH = BILL_CASH + "Thank you for Banking with us...\n";
            BILL_CASH = BILL_CASH + "------------------------------\n\n\n";

            SunmiPrintHelper.getInstance().printText(BILL_CASH, 18F, false, false)

        } catch (e: NullPointerException) {
        }
    }

    private fun Print(input: String) {
        var iLen = 0
        var iCount = 0
        var iPos = 0
        val _data = input.toByteArray()
        iLen = _data.size
        iLen += 4
        val dataArr = IntArray(iLen)
        dataArr[0] = 0x1B.toByte().toInt()
        dataArr[1] = 0x21.toByte().toInt()
        dataArr[2] = 0x00.toByte().toInt()
        iCount = 3
        while (iCount < iLen - 1) {
            dataArr[iCount] = _data[iPos].toInt()
            iCount++
            iPos++
        }
        dataArr[iCount] = 0x0A.toByte().toInt()
        palmtecandro.jnidevDataWrite(dataArr, iLen)
    }

    public fun setLayoutForBalance(){
        balanceVisibility.set(View.GONE)
    }
    public fun setLayoutForCashDeposit(){
        balanceVisibility.set(View.VISIBLE)
    }

    class ReceiptViewmodelFactory: ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RecieptViewmodel::class.java))
            {
                return RecieptViewmodel() as T
            }
            throw IllegalStateException("unknown viewmodel class")
        }

    }

}
package com.finwin.doorstep.riviresa.home.home_activity

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ui.AppBarConfiguration
import com.finwin.doorstep.riviresa.R
import com.finwin.doorstep.riviresa.databinding.ActivityHomeBinding
import com.finwin.doorstep.riviresa.home.agent_management.AgentManagementFragment
import com.finwin.doorstep.riviresa.home.bc_report.BcReportFragment
import com.finwin.doorstep.riviresa.home.customer_creation.CustomerCreationActivity
import com.finwin.doorstep.riviresa.home.enquiry.enquuiry_fragment.EnquiryFragment
import com.finwin.doorstep.riviresa.home.home_fragment.HomeFragment
import com.finwin.doorstep.riviresa.home.jlg.JlgFragment
import com.finwin.doorstep.riviresa.home.transactions.TransactionsFragment
import com.finwin.doorstep.riviresa.login.LoginActivity
import com.finwin.doorstep.riviresa.logout_listner.BaseActivity
import com.finwin.doorstep.riviresa.utils.Constants
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.nav_header_main.view.*


class HomeActivity : BaseActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    //lateinit var activityHomeBinding:ActivityHomeBinding
    var navMenuViewmodel: NavMenuViewmodel =
        NavMenuViewmodel()
    lateinit var homeViewmodel: HomeActivityViewmodel

    companion object {
        lateinit var activityHomeBinding: ActivityHomeBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        navMenuViewmodel =
            NavMenuViewmodel();
        //contentMainBinding.setViewmodel();
        homeViewmodel = ViewModelProvider(this).get(HomeActivityViewmodel::class.java)

        val sharedPreferences: SharedPreferences = getSharedPreferences(
            Constants.PREFERENCE_NAME,
            Context.MODE_PRIVATE
        )

        activityHomeBinding.navView.tv_agent_id.text = sharedPreferences.getString(
            Constants.AGENT_ID,
            ""
        )
        activityHomeBinding.navView.tv_agent_name.text = sharedPreferences.getString(
            Constants.AGENT_NAME,
            ""
        )
        activityHomeBinding.viewmodel = homeViewmodel
        activityHomeBinding.headerMain.viewmodel = navMenuViewmodel
        val fragmentManager = supportFragmentManager
        val fragmentTransaction =
            fragmentManager.beginTransaction()

        val fragment = HomeFragment()
        fragmentTransaction.add(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
        val drawer: DrawerLayout = activityHomeBinding.drawerLayout
        val navigationView: NavigationView = activityHomeBinding.navView


        navigationView.setNavigationItemSelectedListener(this)
        activityHomeBinding.appBar.imgMenu.setOnClickListener {
            drawer.openDrawer(
                GravityCompat.START
            )
        }

        navMenuViewmodel.mAction.observe(this, Observer {
            drawer.close()

            when (it.action) {
                HomeAction.CLICK_TRANSACTION -> {
                    val myFragment: Fragment = TransactionsFragment.newInstance()

                    supportFragmentManager.popBackStack(
                        null,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frame_layout,
                        myFragment
                    ).addToBackStack(null).commit()
                }
                HomeAction.CLICK_ENQUIRY -> {
                    val myFragment: Fragment =
                        EnquiryFragment()
                    supportFragmentManager.popBackStack(
                        null,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frame_layout,
                        myFragment
                    ).addToBackStack(null).commit()
                }
                HomeAction.CLICK_BC_REPORT -> {
                    val myFragment: Fragment =
                        BcReportFragment.newInstance()
                    supportFragmentManager.popBackStack(
                        null,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frame_layout,
                        myFragment
                    ).addToBackStack(null).commit()
                }
                HomeAction.CLICK_AGENT -> {
                    val myFragment: Fragment =
                        AgentManagementFragment.newInstance()
                    supportFragmentManager.popBackStack(
                        null,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frame_layout,
                        myFragment
                    ).addToBackStack(null).commit()
                }
                HomeAction.CLICK_LOGOUT -> {
                    logoutDialog()
                }

                HomeAction.CLICK_JLG_LOAN -> {
                    val myFragment: Fragment =
                        JlgFragment.newInstance()
                    supportFragmentManager.popBackStack(
                        null,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frame_layout,
                        myFragment
                    ).addToBackStack(null).commit()
                }
                HomeAction.CLICK_CUSTOMER_CREATION -> {
                    val intent = Intent(this@HomeActivity, CustomerCreationActivity::class.java)
                    startActivity(intent)
                }
                HomeAction.CLICK_JLG -> {
                    val myFragment: Fragment =
                        JlgFragment.newInstance()
                    supportFragmentManager.popBackStack(
                        null,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frame_layout,
                        myFragment
                    ).addToBackStack(null).commit()
                }
            }

        })
    }

    override fun onStop() {
        super.onStop()
        navMenuViewmodel.mAction.value = HomeAction(HomeAction.DEFAULT)
    }

    override fun onBackPressed() {
        val f =
            supportFragmentManager.findFragmentById(R.id.frame_layout)
        if (f is HomeFragment) {
            logoutDialog()
        } else {
            super.onBackPressed()
        }
    }

    private fun logoutDialog() {
        AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Logout?")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Yes"
            ) { dialog, which ->

                val intent = Intent(applicationContext, LoginActivity::class.java)
                intent.addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP
                    //or Intent.FLAG_ACTIVITY_NEW_TASK
                )
                startActivity(intent)
            }
            .setNegativeButton("No", null)
            .show()
    }

}

private fun NavigationView.setNavigationItemSelectedListener(homeActivity: HomeActivity) {
}

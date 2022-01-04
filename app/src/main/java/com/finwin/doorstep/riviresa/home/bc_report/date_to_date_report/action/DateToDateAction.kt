package com.finwin.doorstep.riviresa.home.bc_report.date_to_date_report.action

import com.finwin.doorstep.riviresa.home.bc_report.daily_report.pojo.BcReportResponse


class DateToDateAction {
    companion object{
        public var DEFAULT: Int = -1
        public var API_ERROR: Int = 1
        public var BC_REPORT_SUCCESS: Int = 2

    }
    var action: Int? = null
    var error:String ? = null
    var bcReportResponse: BcReportResponse  ? = null


    constructor(action: Int?, error: String) {
        this.action = action
        this.error = error
    }

    constructor(action: Int?, bcReportResponse: BcReportResponse) {
        this.action = action
        this.bcReportResponse = bcReportResponse
    }

    constructor(action: Int?) {
        this.action = action
    }


}
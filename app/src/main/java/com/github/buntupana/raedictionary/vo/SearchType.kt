package com.github.buntupana.raedictionary.vo

import android.support.annotation.IntDef


class SearchType {

    companion object {

        @IntDef(EXACT, START, END, CONTAIN)
        @Retention(AnnotationRetention.SOURCE)
        annotation class Type

        const val EXACT: Int = 30
        const val START: Int = 31
        const val END: Int = 32
        const val CONTAIN: Int = 33
    }

}
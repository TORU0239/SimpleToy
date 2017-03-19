package io.toru.simpletoy.framework.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.toru.simpletoy.framework.Const

/**
 * Created by wonyoung on 2017. 3. 19..
 */
abstract class BaseActivity : AppCompatActivity(), Const {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutID())
    }

    abstract fun getLayoutID():Int
}
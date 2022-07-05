package com.hp.printercontro.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.hp.printercontro.R
import com.hp.printercontro.utils.Blocked
import com.hp.printercontro.utils.Empty
import com.hp.printercontro.utils.Loaded
import com.hp.printercontro.view_model.LeprechaunViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class LeprechaunLoading : AppCompatActivity() {
    private lateinit var leprechaunViewModel: LeprechaunViewModel

    @Inject
    @Named("key")
    lateinit var key: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loading_leprechaun)
        leprechaunViewModel = ViewModelProvider(this)[LeprechaunViewModel::class.java]
        lifecycleScope.launch {
            leprechaunViewModel.state.collect { state ->
                when (state) {
                    Empty -> {}
                    is Loaded -> {
                        val intent = Intent(this@LeprechaunLoading, LeprechaunWeb::class.java)
                        intent.putExtra(key, state.data)
                        startActivity(intent)
                        finish()
                    }
                    is Blocked -> {
                        val intent = Intent(
                            this@LeprechaunLoading,
                            LeprechaunGame::class.java
                        )
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }
}
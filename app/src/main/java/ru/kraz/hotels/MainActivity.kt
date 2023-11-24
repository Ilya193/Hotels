package ru.kraz.hotels

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.kraz.common.presentation.BaseFragment
import ru.kraz.hotels.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MainViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        supportFragmentManager.setFragmentResultListener(
            BaseFragment.ACTION_BACK_REQUEST_KEY,
            this
        ) { _, bundle ->
            supportActionBar?.setDisplayHomeAsUpEnabled(bundle.getBoolean(BaseFragment.ACTION_BACK_KEY))
        }

        viewModel.init(savedInstanceState == null)

        viewModel.liveData().observe(this) {
            it.show(supportFragmentManager, R.id.fragmentContainer)
        }
    }

    override fun onResume() {
        super.onResume()
        supportFragmentManager.setFragmentResultListener(
            BaseFragment.TOOLBAR_TITLE_REQUEST_KEY,
            this
        ) { _, bundle ->
            binding.toolbar.title = bundle.getString(BaseFragment.TOOLBAR_TITLE_KEY)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            supportFragmentManager.popBackStack()
        return super.onOptionsItemSelected(item)
    }
}
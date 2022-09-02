package android.ptc.com.ptcflixing.ui

import android.os.Bundle
import android.ptc.com.ptcflixing.R
import android.ptc.com.ptcflixing.databinding.ActivityJumiaTestBinding
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class JumiaTestActivity : AppCompatActivity() {
    private val sharedViewModel by viewModels<SharedViewModel>()
    private lateinit var binding: ActivityJumiaTestBinding

    //Lazy val
    private val navHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
    }
    private val navController by lazy {
        navHostFragment.findNavController()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        //SplashScreen
        installSplashScreen().setKeepOnScreenCondition {
            sharedViewModel.onEvent(SharedViewModel.SharedEvents.SplashScreen)
            sharedViewModel.showSplashScreen.value
        }
        super.onCreate(savedInstanceState)
        binding = ActivityJumiaTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.mainToolbar.setupWithNavController(navController)

        lifecycleScope.launch {
            lifecycleScope.launchWhenStarted {
                sharedViewModel.channel.collect { channel ->
                    when (channel) {
                        is SharedViewModel.SharedChannel.Message -> {
                            Snackbar.make(binding.root, channel.message, Snackbar.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }
        }

        sharedViewModel.connectionState.observe(this) { connectionState ->
            if (!connectionState.condition)
                Snackbar.make(binding.root, connectionState.message, Snackbar.LENGTH_LONG)
                    .show()
        }

    }


    override fun onNavigateUp(): Boolean {
        return navController.navigateUp() || super.onNavigateUp()
    }
}

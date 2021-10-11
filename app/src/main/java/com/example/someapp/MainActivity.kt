package com.example.someapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.someapp.data.ApiService
import com.example.someapp.databinding.ActivityMainBinding
import com.example.someapp.domain.toDomain
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    private lateinit var bindingView : ActivityMainBinding
    private val navigationIds = setOf(R.id.nav_list)
    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
    }

    private lateinit var appBarConfiguration: AppBarConfiguration



    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_MiBUS_NoActionBar)
        super.onCreate(savedInstanceState)
        bindingView = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingView.root)

        setSupportActionBar(bindingView.toolbar)
        navigationFrameworkSetUp()

//        testApi()
    }

    override fun onSupportNavigateUp() = navController.navigateUp(appBarConfiguration)
            || super.onSupportNavigateUp()


    private fun testApi(){
        launch {
            runCatching { ApiService.create().getDataList().awaitResponse() }.fold(
                onSuccess = {
                    val resultList = it.body()?.let { response ->
                        response.map {
                                netModel -> netModel.toDomain()
                        }
                    }.orEmpty()
                    Log.d("TEST", "Succed: $resultList")
                },
                onFailure = { Log.d("TEST", "Error $it")}
            )
        }
    }

    private fun navigationFrameworkSetUp() {
        appBarConfiguration = AppBarConfiguration(navigationIds)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

}
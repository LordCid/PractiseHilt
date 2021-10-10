package com.example.someapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.someapp.data.ApiService
import com.example.someapp.databinding.ActivityMainBinding
import com.example.someapp.domain.ResultState
import com.example.someapp.domain.toDomain
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    private lateinit var bindingView : ActivityMainBinding
    private val navigationIds = setOf(R.id.nav_list)
    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
    }

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingView = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingView.root)

        setToolBar()
        navigationFrameworkSetUp()

        testApi()
    }

    private fun setToolBar() {
        val toolBar: Toolbar = bindingView.toolbar
        setSupportActionBar(toolBar)
    }

    private fun testApi(){
        launch {
            runCatching { ApiService.create().getDataList().awaitResponse() }.fold(
                onSuccess = {
                    val resultList = it.body()?.let { response ->
                        response.map {
                                netModel -> netModel.toDomain()
                        }
                    }.orEmpty()
                    print(resultList)
                },
                onFailure = { print("Error $it") }
            )
        }
    }

    private fun navigationFrameworkSetUp() {
        appBarConfiguration = AppBarConfiguration(navigationIds)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

}
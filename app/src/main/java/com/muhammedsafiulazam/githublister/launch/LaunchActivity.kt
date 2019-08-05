package com.muhammedsafiulazam.githublister.launch

import android.os.Bundle
import com.muhammedsafiulazam.githublister.Knowledge
import com.muhammedsafiulazam.githublister.R
import com.muhammedsafiulazam.githublister.activity.BaseActivity
import com.muhammedsafiulazam.githublister.feature.repositorylist.RepositoryListActivity

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

class LaunchActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
    }

    override fun onStart() {
        super.onStart()

        // Entry activity.
        Knowledge.getActivityManager().loadActivity(RepositoryListActivity::class.java)
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}

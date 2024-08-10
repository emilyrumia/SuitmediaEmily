package com.example.suitmediaapp.third

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.suitmediaapp.ViewModelFactory
import com.example.suitmediaapp.adapter.ItemUserAdapter
import com.example.suitmediaapp.api.ResultState
import com.example.suitmediaapp.api.response.ProfileData
import com.example.suitmediaapp.api.response.UserResponse
import com.example.suitmediaapp.databinding.ActivityThirdBinding
import com.example.suitmediaapp.model.User
import com.example.suitmediaapp.second.SecondActivity

class ThirdActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThirdBinding
    private val viewModel by viewModels<ThirdViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var adapter: ItemUserAdapter
    private lateinit var name: String
    private var currentPage = 1
    private val perPage = 10
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSwipeRefreshLayout()

        name = intent.getStringExtra(EXTRA_NAME).toString()

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        adapter = ItemUserAdapter(emptyList()) { user: User ->
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra(SecondActivity.EXTRA_NAME, name)
            intent.putExtra(SecondActivity.EXTRA_SELECTED, "${user.firstName} ${user.lastName}")
            startActivity(intent)
        }

        setupRecyclerView()
        loadUsers(currentPage, perPage)
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                if (!isLoading && lastVisibleItem + 1 == totalItemCount) {
                    currentPage++
                    loadUsers(currentPage, perPage)
                }
            }
        })
    }

    private fun setupSwipeRefreshLayout() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            currentPage = 1
            viewModel.refreshData()
            loadUsers(currentPage, perPage)
        }
    }

    private fun loadUsers(page: Int, perPage: Int) {
        isLoading = true
        viewModel.getUser(page, perPage).observe(this) { resultState ->
            when (resultState) {
                is ResultState.Loading -> {
                    binding.swipeRefreshLayout.isRefreshing = true
                }
                is ResultState.Success -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    updateUserList(resultState.data)
                    isLoading = false
                }
                is ResultState.Error -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    showDialog("Error loading data!")
                }
                is ResultState.Empty -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    showDialog("There are no more items to display!")
                    isLoading = false
                }
            }
        }
    }

    private fun showDialog(message: String) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }

    private fun convertProfileDataToUser(profileDataList: List<ProfileData>): List<User> {
        return profileDataList.map { profileData ->
            User(
                firstName = profileData.firstName,
                lastName = profileData.lastName,
                avatar = profileData.avatar,
                email = profileData.email
            )
        }
    }

    private fun updateUserList(userResponse: UserResponse) {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val userList = convertProfileDataToUser(userResponse.data)
        adapter = ItemUserAdapter(userList) { user: User ->
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra(SecondActivity.EXTRA_NAME, name)
            intent.putExtra(SecondActivity.EXTRA_SELECTED, "${user.firstName} ${user.lastName}")
            startActivity(intent)
        }
        binding.recyclerView.adapter = adapter
    }

    companion object {
        const val EXTRA_NAME = "User"
    }

}


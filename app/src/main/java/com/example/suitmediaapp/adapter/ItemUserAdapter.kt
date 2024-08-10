package com.example.suitmediaapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.suitmediaapp.R
import com.example.suitmediaapp.model.User

class ItemUserAdapter(
    private val users: List<User>,
    private val onItemClick: (User) -> Unit
) : RecyclerView.Adapter<ItemUserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int = users.size

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userNameTextView: TextView = itemView.findViewById(R.id.full_name)
        private val userEmailTextView: TextView = itemView.findViewById(R.id.email)
        private val userAvatarImageView: ImageView = itemView.findViewById(R.id.avatar_image)

        fun bind(user: User) {
            userNameTextView.text = "${user.firstName} ${user.lastName}"
            userEmailTextView.text = user.email
            Glide.with(itemView.context)
                .load(user.avatar)
                .placeholder(R.color.black)
                .into(userAvatarImageView)

            itemView.setOnClickListener {
                onItemClick(user)
            }
        }
    }
}

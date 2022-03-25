package com.example.task14

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.task14.databinding.RowBinding

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserHolder>() {
    private val users = ArrayList<UserData>()

    class UserHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = RowBinding.bind(item)
        fun bind(userData: UserData) = with(binding) {
            tvUserLogin.text = userData.login
            tvUserSurname.text = userData.surname
            tvUserName.text = userData.name
            tvUserEmail.text = userData.email
            tvUserPassword.text = userData.password
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
        return UserHolder(view)
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int {
        return users.size
    }

    fun addUser(user: UserData) {
        users.add(user)
        notifyDataSetChanged()
    }
}
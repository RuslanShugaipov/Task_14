package com.example.task14

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task14.databinding.FragmentUserProfileBinding
import com.google.gson.GsonBuilder
import org.json.JSONException
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStreamReader
import java.lang.StringBuilder

class UserProfileFragment : Fragment() {
    private lateinit var binding: FragmentUserProfileBinding
    private val adapter = UserAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        binding.btnGoToHome.setOnClickListener{
            Navigation.findNavController(binding.root).navigate(R.id.navigateToHomeFragment)
        }

        binding.btnSignOut.setOnClickListener{
            Navigation.findNavController(binding.root).navigate(R.id.navigateToLoginFragment)
        }

        try {
            initRV()
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        } catch (jsonException: JSONException) {
            jsonException.printStackTrace()
        }

        return binding.root
    }

    private fun getUserData(): UserData {
        val fileName = "user.json"
        var fis: FileInputStream? = null
        fis = context?.openFileInput(fileName)
        val isr = InputStreamReader(fis)
        val bufferedReader = BufferedReader(isr)

        val jsonString = StringBuilder()
        var text: String? = null

        while ({ text = bufferedReader.readLine();text }() != null) {
            jsonString.append(text)
        }

        val builder = GsonBuilder()
        val gson = builder.create()
        return gson.fromJson(jsonString.toString(), UserData::class.java)
    }

    private fun initRV() = with(binding) {
        recyclerView.layoutManager = LinearLayoutManager(this@UserProfileFragment.context)
        recyclerView.adapter = adapter
        adapter.addUser(getUserData())
    }

    companion object {
        @JvmStatic
        fun newInstance() = UserProfileFragment()
    }
}
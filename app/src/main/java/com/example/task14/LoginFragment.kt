package com.example.task14

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.task14.databinding.FragmentLoginBinding
import com.google.gson.GsonBuilder
import java.io.*
import java.lang.StringBuilder

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        loadData()

        binding.btnSignUp.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.navigateToRegistrationFragment)
        }

        binding.btnSignIn.setOnClickListener {
            try {
                val userData = getUserData()
                if (binding.etLogin.text.toString() == userData.login
                    && binding.etPassword.text.toString() == userData.password
                ) {
                    Navigation.findNavController(binding.root).navigate(R.id.navigateToHomeFragment)
                }
            } catch (ioException: IOException) {
                ioException.printStackTrace()
            }
        }
        return binding.root
    }

    private fun getUserData(): UserData {
        val fileName = "user.json"
        var fis: FileInputStream? = null
        fis = context?.openFileInput(fileName)
        var isr = InputStreamReader(fis)
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

    override fun onPause() {
        super.onPause()

        val insertedLogin = binding.etLogin.text.toString()
        val insertedPassword = binding.etPassword.text.toString()

        val sharedPrefs = activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPrefs?.edit()
        editor?.putString("STR_LOGINLF", insertedLogin)
        editor?.putString("STR_PASSWORDLF", insertedPassword)
        editor?.apply()
    }

    private fun loadData() {
        val sharedPrefs = activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val savedLogin = sharedPrefs?.getString("STR_LOGINLF", null)
        val savedPassword = sharedPrefs?.getString("STR_PASSWORDLF", null)

        binding.etLogin.setText(savedLogin)
        binding.etPassword.setText(savedPassword)
    }

    companion object {
        @JvmStatic
        fun newInstance() = LoginFragment()
    }
}
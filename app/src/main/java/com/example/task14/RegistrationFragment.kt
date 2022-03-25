package com.example.task14

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.task14.databinding.FragmentRegistrationBinding
import com.google.gson.GsonBuilder
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception


class RegistrationFragment : Fragment() {
    private lateinit var binding: FragmentRegistrationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)

        loadData()

        binding.btnSignUp.setOnClickListener {
            val newUserData = UserData(
                login = binding.etLogin.text.toString(),
                surname = binding.etSurname.text.toString(),
                name = binding.etName.text.toString(),
                email = binding.etEmail.text.toString(),
                password = binding.etPassword.text.toString()
            )
            try {
                if (newUserData.login.isNotEmpty() && newUserData.surname.isNotEmpty() &&
                    newUserData.name.isNotEmpty() && newUserData.email.isNotEmpty() &&
                    newUserData.password.isNotEmpty()
                ) {
                    writeUserData(newUserData)
                }
            } catch (ioException: IOException) {
                ioException.printStackTrace()
            }
            Navigation.findNavController(binding.root).navigate(R.id.navigateToLoginFragment)
        }
        return binding.root
    }

    private fun writeUserData(userData: UserData) {
        val builder = GsonBuilder()
        val gson = builder.create()
        val jsonString = gson.toJson(userData)

        val fileName = "user.json"
        val fos: FileOutputStream
        try {
            fos = requireContext().openFileOutput(fileName, Context.MODE_PRIVATE)
            fos.write(jsonString.toByteArray())
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e:Exception){
            e.printStackTrace()
        }
    }

    override fun onPause() {
        super.onPause()

        val insertedLogin = binding.etLogin.text.toString()
        val insertedPassword = binding.etPassword.text.toString()
        val insertedSurname = binding.etSurname.text.toString()
        val insertedName = binding.etName.text.toString()
        val insertedEmail = binding.etEmail.text.toString()

        val sharedPrefs = activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPrefs?.edit()
        editor?.putString("STR_LOGINRF", insertedLogin)
        editor?.putString("STR_PASSWORDRF", insertedPassword)
        editor?.putString("STR_SURNAME", insertedSurname)
        editor?.putString("STR_NAME", insertedName)
        editor?.putString("STR_EMAIL", insertedEmail)
        editor?.apply()
    }

    private fun loadData() {
        val sharedPrefs = activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val savedLogin = sharedPrefs?.getString("STR_LOGINRF", null)
        val savedPassword = sharedPrefs?.getString("STR_PASSWORDRF", null)
        val savedSurname = sharedPrefs?.getString("STR_SURNAME", null)
        val savedName = sharedPrefs?.getString("STR_NAME", null)
        val savedEmail = sharedPrefs?.getString("STR_EMAIL", null)

        binding.etLogin.setText(savedLogin)
        binding.etPassword.setText(savedPassword)
        binding.etSurname.setText(savedSurname)
        binding.etName.setText(savedName)
        binding.etEmail.setText(savedEmail)
    }

    companion object {
        @JvmStatic
        fun newInstance() = RegistrationFragment()
    }
}
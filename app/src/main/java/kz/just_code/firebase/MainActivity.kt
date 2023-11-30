package kz.just_code.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference.CompletionListener
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.IgnoreExtraProperties
import kz.just_code.firebase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val userInfo = UserInfo()
        binding.save.setOnClickListener {
            binding.progress.isVisible = true
            userInfo.saveData(getUser()) {
                binding.progress.isVisible = false
            }
        }
        binding.get.setOnClickListener {
            userInfo.getData()
        }
        userInfo.getDataLiveData.observe(this) {
            binding.user.text = it?.toString()
        }
        userInfo.updateDataLiveData.observe(this) {
            binding.userUpdate.text = it?.toString()
        }
    }

    private fun getUser() = UserData(
        name = "Alice",
        age = 30,
        email = "ali@gmail.com",
        address = Address("123 Main Street", "New York", "12345"),
        interests = listOf("Reading", "Traveling", "Coding"),
        phoneNumber = "+1234567890",
        friends = listOf(Friend("Bob", 28), Friend("Charlie", 32))
    )

}

class UserInfo : Wrapper<UserData>() {
    override fun getTableName(): String = "Database name"

    override fun getClassType(): Class<UserData> = UserData::class.java
}

data class UserData(
    var name: String? = null,
    var age: Int? = null,
    var email: String? = null,
    var address: Address? = null,
    var interests: List<String>? = null,
    var phoneNumber: String? = null,
    var friends: List<Friend>? = null
){
    override fun toString(): String{
return "Name: $name, age: $age, email: $email, address: $address, interests: $interests, phone number: $phoneNumber, friends: $friends"
    }
}

data class Address(
    var street: String? = null,
    var city: String? = null,
    var zipCode: String? = null
)

data class Friend(
    var friendName: String? = null,
    var friendAge: Int? = null
)
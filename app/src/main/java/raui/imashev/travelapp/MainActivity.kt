package raui.imashev.travelapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import raui.imashev.travelapp.fragments.StartFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, StartFragment())
                .commitNow()
        }
    }
}
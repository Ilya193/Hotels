package ru.kraz.hotels

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import ru.kraz.feature_hotel.presentation.HotelFragment
import ru.kraz.feature_paid.presentation.PaidFragment
import ru.kraz.feature_reservation.presentation.ReservationFragment
import ru.kraz.feature_rooms.presentation.RoomsFragment

interface Screen {
    fun show(supportFragmentManager: FragmentManager, container: Int) = Unit

    abstract class Replace(
        private val fragmentClass: Class<out Fragment>,
    ) : Screen {
        override fun show(supportFragmentManager: FragmentManager, container: Int) {
            supportFragmentManager.beginTransaction()
                .replace(container, fragmentClass.getDeclaredConstructor().newInstance()).commit()
        }
    }

    abstract class ReplaceWithAddToBackStack(
        private val fragment: Fragment,
    ) : Screen {
        override fun show(supportFragmentManager: FragmentManager, container: Int) {
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.slide_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.slide_out
                )
                .replace(container, fragment)
                .addToBackStack(null).commit()
        }
    }

    abstract class ReplaceWithAddToBackStackWithClass(
        fragment: Class<out Fragment>,
    ) : ReplaceWithAddToBackStack(fragment.getDeclaredConstructor().newInstance())

    abstract class ReplaceWithClear(
        fragment: Class<out Fragment>,
    ) : Replace(fragment) {
        override fun show(supportFragmentManager: FragmentManager, container: Int) {
            repeat(supportFragmentManager.backStackEntryCount) {
                supportFragmentManager.popBackStack()
            }
            super.show(supportFragmentManager, container)
        }
    }

    data object Pop : Screen {
        override fun show(supportFragmentManager: FragmentManager, container: Int) {
            supportFragmentManager.popBackStack()
        }
    }

    data object Coup : Screen
}

data object HotelScreen : Screen.Replace(HotelFragment::class.java)
data class RoomsScreen(private val title: String) :
    Screen.ReplaceWithAddToBackStack(RoomsFragment.newInstance(title))
data object ReservationScreen : Screen.ReplaceWithAddToBackStackWithClass(ReservationFragment::class.java)
data object PaidScreen : Screen.ReplaceWithAddToBackStackWithClass(PaidFragment::class.java)
data object HotelScreenWithClear : Screen.ReplaceWithClear(HotelFragment::class.java)
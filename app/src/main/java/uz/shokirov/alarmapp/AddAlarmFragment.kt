package uz.shokirov.alarmapp

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import uz.shokirov.alarmapp.databinding.FragmentAddAlarmBinding
import uz.shokirov.models.Alarms
import uz.shokirov.utils.AlarmCash
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddAlarmFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddAlarmFragment : Fragment() {
    lateinit var binding: FragmentAddAlarmBinding

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentAddAlarmBinding.inflate(layoutInflater)

        AlarmCash.init(context)
        binding.hourPicker.minValue = 0
        binding.hourPicker.maxValue = 23
        binding.minutePicker.minValue = 0
        binding.minutePicker.maxValue = 59
        binding.minutePicker.textColor = Color.WHITE
        binding.hourPicker.textColor = Color.WHITE

        val calendar = Calendar.getInstance()
        calendar.time = Date()
        val hh = SimpleDateFormat("HH").format(calendar.time).toInt()
        val mm = SimpleDateFormat("mm").format(calendar.time).toInt()

        binding.minutePicker.value = mm
        binding.hourPicker.value = hh

        var time = SystemClock.elapsedRealtime() + 1000
        val intent = Intent(context, MyReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 1, intent, 0)
        val alarmManager = activity?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                time,
                10000,
                pendingIntent
        )

        binding.tvShowTime.text =
                "Uyg'otgich ${binding.hourPicker.value}:${binding.minutePicker.value}da ishga tushadi"

        binding.hourPicker.setOnValueChangedListener { p0, p1, p2 ->
            binding.tvShowTime.text =
                    "Uyg'otgich ${binding.hourPicker.value}:${binding.minutePicker.value}da ishga tushadi"
        }
        binding.minutePicker.setOnValueChangedListener { p0, p1, p2 ->
            binding.tvShowTime.text =
                    "Uyg'otgich ${binding.hourPicker.value}:${binding.minutePicker.value}da ishga tushadi"
        }
        binding.imageCancel.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.imageOk.setOnClickListener {
            var name = binding.card.text.toString().trim()
            if (name.isNotEmpty()) {
                val hour = binding.hourPicker.value
                val minute = binding.minutePicker.value
                val list = AlarmCash.alarmList
                list.add(
                        Alarms(
                                binding.card.text.toString().trim(),
                                hour,
                                minute,
                                true,
                                binding.vibrationSwitch.isChecked
                        )
                )
                AlarmCash.alarmList = list
                Toast.makeText(context, "Saqlandi", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            } else {
                Toast.makeText(context, "Eslatma uchun nom bering", Toast.LENGTH_SHORT).show()
            }
        }


        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddAlarmFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                AddAlarmFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
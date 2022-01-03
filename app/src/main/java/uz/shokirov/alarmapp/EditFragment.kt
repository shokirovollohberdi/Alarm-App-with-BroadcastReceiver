package uz.shokirov.alarmapp

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import uz.shokirov.alarmapp.databinding.FragmentEditBinding
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
 * Use the [EditFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditFragment : Fragment() {
    lateinit var binding: FragmentEditBinding
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditBinding.inflate(layoutInflater)

        var position = arguments?.getInt("key")
        AlarmCash.init(context)
        val list = AlarmCash.alarmList

        binding.hourPicker.minValue = 0
        binding.hourPicker.maxValue = 23
        binding.minutePicker.minValue = 0
        binding.minutePicker.maxValue = 59
        binding.minutePicker.textColor = Color.WHITE
        binding.hourPicker.textColor = Color.WHITE


        binding.minutePicker.value = list[position!!].min!!
        binding.hourPicker.value = list[position!!].hour!!
        binding.vibrationSwitch.isChecked = list[position!!].isRun
        binding.card.setText(list[position!!].name)

        binding.imageOk.setOnClickListener {
            var name = binding.card.text.toString().trim()
            var min = binding.minutePicker.value
            var hour = binding.hourPicker.value
            var vibration = binding.vibrationSwitch.isEnabled
            if (name.isNotEmpty()){
               /* list[position].name = name
                list[position].min = min
                list[position].hour = hour
                list[position].vibration = vibration*/
                val alarms = Alarms(name,hour,min,true,vibration)
                list.remove(list[position])
                list.add(alarms)
                AlarmCash.alarmList = list
                findNavController().popBackStack()
            }else{
                Toast.makeText(context, "Uyg'ongich nomi yo'q", Toast.LENGTH_SHORT).show()
            }
        }
        binding.imageCancel.setOnClickListener {
            findNavController().popBackStack()
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
         * @return A new instance of fragment EditFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
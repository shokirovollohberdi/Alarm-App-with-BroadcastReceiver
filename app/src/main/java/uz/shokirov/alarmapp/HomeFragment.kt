package uz.shokirov.alarmapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import uz.shokirov.adapter.OnClick
import uz.shokirov.adapter.RvAdapter
import uz.shokirov.alarmapp.databinding.FragmentHomeBinding
import uz.shokirov.models.Alarms
import uz.shokirov.utils.AlarmCash

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var adapter: RvAdapter

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
            savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        binding.imgAdd.setOnClickListener {
            findNavController().navigate(R.id.addAlarmFragment)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        AlarmCash.init(context)
        var list = AlarmCash.alarmList

        list.sortBy {
            "${it.hour}:${it.min}"
        }
        adapter = RvAdapter(list, object : OnClick {
            override fun deteleForLongClick(alarms: Alarms, position: Int) {
                AlarmCash.init(context)
                val mL = AlarmCash.alarmList
                mL.removeAt(position)
                AlarmCash.alarmList = mL
                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
                onResume()
            }

            override fun switch(alarms: Alarms, position: Int) {
                AlarmCash.init(context)
                val ml = AlarmCash.alarmList
                ml[position] = alarms
                AlarmCash.alarmList = ml
                onResume()
            }

            override fun click(alarms: Alarms, position: Int) {
                findNavController().navigate(R.id.editFragment, bundleOf("key" to position))
            }
        })
        binding.rv.adapter = adapter
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                HomeFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
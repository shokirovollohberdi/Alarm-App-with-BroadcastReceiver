package uz.shokirov.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import uz.shokirov.alarmapp.databinding.ItemRvBinding
import uz.shokirov.models.Alarms

class RvAdapter(var list: ArrayList<Alarms>,val onClick: OnClick) : RecyclerView.Adapter<RvAdapter.Vh>() {
    inner class Vh(var itemRv: ItemRvBinding) : RecyclerView.ViewHolder(itemRv.root) {
        fun onBind(alarms: Alarms, position: Int) {
            itemRv.itemName.text = alarms.name
            itemRv.itemTime.text = "${alarms.hour}:${alarms.min}"
            itemRv.itemSwicht.isChecked = alarms.isRun
            itemRv.itemSwicht.setOnCheckedChangeListener { p0, p1 ->
                alarms.isRun = p1
                onClick.switch(list[position],position)
            }
            itemRv.root.setOnClickListener {
                onClick.click(list[position],position)
            }
            itemRv.root.setOnLongClickListener {
                onClick.deteleForLongClick(list[position],position)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent?.context), parent, false))
    }


    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size
}interface OnClick{
    fun deteleForLongClick(alarms: Alarms,position: Int)
    fun switch(alarms: Alarms,position: Int)
    fun click(alarms: Alarms,position: Int)
}
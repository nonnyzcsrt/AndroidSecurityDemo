package th.co.mfec.androidsecuritydemo.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.menu.view.*
import th.co.mfec.androidsecuritydemo.R

class MenuAdapter(
    val context: Context,
    private val items: Array<String>,
    private val itemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.menu, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val menuText = items[position]
        holder.getMenuButton.text = menuText
        holder.getMenuButton.setOnClickListener {
            itemClickListener.onItemClicked(menuText, position)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val getMenuButton = view.btn_menu!!
    }
}

interface OnItemClickListener {
    fun onItemClicked(menuTxt: String, position: Int)
}


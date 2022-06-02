package uz.gita.offlinedictionarymvvm.presentation.ui.adapter

import android.annotation.SuppressLint
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uz.gita.offlinedictionarymvvm.R

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    var cursor: Cursor? = null
    private var onClickItemListener: ((String, Int) -> Unit)? = null

    @SuppressLint("Range")
    inner class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val categoryTitleEng: TextView = view.findViewById(R.id.categoryTitle)
        private val categoryTitleUz: TextView = view.findViewById(R.id.categoryTitleUz)
        private val imageCategory: ImageView = view.findViewById(R.id.imageCategory)

        init {
            cursor?.moveToPosition(absoluteAdapterPosition)
            view.setOnClickListener {
                cursor?.moveToPosition(absoluteAdapterPosition)
                onClickItemListener?.invoke(
                    categoryTitleEng.text.toString(),
                    cursor?.getInt(cursor?.getColumnIndex("_id")!!)!!
                )
            }
        }

        @SuppressLint("Range")
        fun bind() {
            cursor?.let {
                it.moveToPosition(absoluteAdapterPosition)
                categoryTitleEng.text = it.getString(it.getColumnIndex("cen"))
                categoryTitleUz.text = it.getString(it.getColumnIndex("cfr"))
                imageCategory.setImageResource(it.getString(it.getColumnIndex("ces")).toInt())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        cursor?.let { return it.count }
        return 0
    }

    fun setOnClickItemListener(block: ((String, Int) -> Unit)) {
        onClickItemListener = block
    }
}
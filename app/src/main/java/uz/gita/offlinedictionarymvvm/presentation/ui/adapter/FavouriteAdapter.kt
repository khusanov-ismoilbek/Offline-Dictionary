package uz.gita.offlinedictionarymvvm.presentation.ui.adapter

import android.annotation.SuppressLint
import android.database.Cursor
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uz.gita.offlinedictionarymvvm.R

class FavouriteAdapter : RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder>() {
    var cursor: Cursor? = null
    var query: String? = ""
    private var onClickRememberListener: ((Int) -> Unit)? = null

    @SuppressLint("Range")
    inner class FavouriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val textEng: TextView = view.findViewById(R.id.titleDictionary)
        private val textUz: TextView = view.findViewById(R.id.subTitleDictionary)
        private val remember: ImageView = view.findViewById(R.id.remember)

        init {
            remember.setOnClickListener {
                cursor?.let {
                    it.moveToPosition(absoluteAdapterPosition)
                    onClickRememberListener?.invoke(
                        it.getInt(it.getColumnIndex("_id"))
                    )
                }
            }
        }

        @SuppressLint("Range")
        fun bind() {
            cursor?.let {
                it.moveToPosition(absoluteAdapterPosition)
                textEng.text = if (query == "") {
                    it.getString(it.getColumnIndex("en"))
                } else {
                    val t = it.getString(it.getColumnIndex("en"))
                    val text = SpannableString(t)
                    val color = ForegroundColorSpan(Color.RED)
                    val start = t.lowercase().indexOf(query?.lowercase()!!)
                    val end = start + query!!.length
                    if (start != -1)
                        text.setSpan(color, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    text
                }
                textUz.text = if (query == "") {
                    it.getString(it.getColumnIndex("fr"))
                } else {
                    val t = it.getString(it.getColumnIndex("fr"))
                    val text = SpannableString(t)
                    val color = ForegroundColorSpan(Color.RED)
                    val start = t.lowercase().indexOf(query?.lowercase()!!)
                    val end = start + query!!.length
                    if (start != -1)
                        text.setSpan(color, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    text
                }
                if (it.getInt(it.getColumnIndex("fav")) == 0) {
                    remember.setImageResource(R.drawable.ic_bookmark)
                } else {
                    remember.setImageResource(R.drawable.ic_bookmark_select)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        return FavouriteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_dictionary, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        cursor?.let { return it.count }
        return 0
    }

    fun setOnClickRememberListener(block: (Int) -> Unit) {
        onClickRememberListener = block
    }
}
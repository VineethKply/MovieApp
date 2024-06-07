import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vineeth.movies.R
import com.vineeth.movies.entitys.Movieslist
import com.vineeth.movies.interfaces.MovieClickListner


class MovieListAdapter(context: Context) : RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {
    var context: Context? = null
    var clickListener: MovieClickListner? = null
    private var mList=emptyList<Movieslist>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        this.context=context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_item_movie, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val moviedetails = mList[position]
        holder.name.text = moviedetails.title
        holder.txt_rateing.text = " ${moviedetails.rating}/10"
        context?.let {
            Glide.with(it)
                .load(moviedetails.posterurl)
                .into(holder.img_movie)
        }

        holder.card_layout.setOnClickListener {
            clickListener?.onMovieClick(moviedetails)
        }


    }

    override fun getItemCount(): Int {
        return mList.size
    }



    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val name: TextView = itemView.findViewById(R.id.txt_movie_name)
        val txt_rateing: TextView = itemView.findViewById(R.id.txt_rateing)
        val img_movie: ImageView = itemView.findViewById(R.id.img_movie)
        val card_layout: CardView = itemView.findViewById(R.id.card_layout)

    }

    fun setData(newData: List<Movieslist>){
        mList = newData
        notifyDataSetChanged()
    }

    fun setListner(movieClickListner: MovieClickListner){
        this.clickListener = movieClickListner

    }

}

package com.ikuz.ikuzsmusicapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ikuz.ikuzsmusicapp.R
import com.ikuz.ikuzsmusicapp.models.ArtistModel

class LocalArtistAdapter(
    var ArtistList: ArrayList<ArtistModel>,
    var context: Context
) : RecyclerView.Adapter<LocalArtistAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun getContext():Context = itemView.context

        val artistName: TextView = itemView.findViewById(R.id.artistName)
        val artistNumSong: TextView = itemView.findViewById(R.id.artistNumSong)
        val artistArt: ImageView = itemView.findViewById(R.id.artistArt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(context).inflate(R.layout.artist_rec_item, parent, false)
        return LocalArtistAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ArtistData: ArtistModel = ArtistList[position]
        holder.artistName.text = ArtistData.artist
        holder.artistNumSong.text = ArtistData.num_of_songs.toString() + " Songs"
        holder.artistArt.run {
            Glide
                .with(holder.getContext())
                .load(R.drawable.artist_art)
                .into(this)
        }
    }

    override fun getItemCount(): Int {
        return ArtistList.size
    }
}
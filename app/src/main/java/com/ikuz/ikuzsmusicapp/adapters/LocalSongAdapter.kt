package com.ikuz.ikuzsmusicapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ikuz.ikuzsmusicapp.R
import com.ikuz.ikuzsmusicapp.models.SongModel

class LocalSongAdapter(
    var songList: ArrayList<SongModel>,
    var context: Context
) : RecyclerView.Adapter<LocalSongAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val localSongTitel: TextView = itemView.findViewById<TextView>(R.id.localSongTitel)
        val localSongArtist : TextView = itemView.findViewById<TextView>(R.id.localSongArtist)
        val localSongAlbum : TextView = itemView.findViewById<TextView>(R.id.localSongAlbum)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.song_rec_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val songData : SongModel = songList[position]
        holder.localSongTitel.text = songData.title
        holder.localSongArtist.text = songData.artist
        holder.localSongAlbum.text = songData.album
    }

    override fun getItemCount(): Int {
        return songList.size
    }
}
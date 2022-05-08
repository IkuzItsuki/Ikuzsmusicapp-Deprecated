package com.ikuz.ikuzsmusicapp.adapters

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ikuz.ikuzsmusicapp.R
import com.ikuz.ikuzsmusicapp.models.AlbumModel
import com.ikuz.ikuzsmusicapp.utils.Constant
import java.io.File


class LocalAlbumAdapter(
    var AlbumList : ArrayList<AlbumModel>,
    var context : Context
) : RecyclerView.Adapter<LocalAlbumAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun getContext():Context = itemView.context

        val albumArt : ImageView = itemView.findViewById(R.id.albumArt)
        val albumTitel : TextView = itemView.findViewById(R.id.albumTitel)
        val songsText : TextView = itemView.findViewById(R.id.songsText)
        val artistText : TextView = itemView.findViewById(R.id.artistText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(context).inflate(R.layout.album_rec_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val AlbumData : AlbumModel = AlbumList[position]
        holder.albumTitel.text = AlbumData.album
        holder.songsText.text = AlbumData.numsongs.toString() + " Songs"
        holder.artistText.text = AlbumData.artist
        holder.albumArt.run {
            if (this != null) {
                File(
                    Constant.imaSongDir.absolutePath + "/album_art",
                    File(AlbumData.album).nameWithoutExtension
                ).also {
                    when {
                        it.exists() -> Glide.with(holder.getContext())
                            .load(it)
                            .placeholder(R.drawable.album_p)
                            .into(this)

                        else -> {
                            try {
                                val sArtworkUri =
                                    Uri.parse("content://media/external/audio/albumart")
                                val albumArtUri =
                                    ContentUris.withAppendedId(sArtworkUri, AlbumData._id)
                                Glide
                                    .with(holder.getContext())
                                    .load(albumArtUri)
                                    .placeholder(R.drawable.album_p)
                                    .into(this)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return AlbumList.size
    }
}
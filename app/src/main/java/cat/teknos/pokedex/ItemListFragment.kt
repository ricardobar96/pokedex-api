package cat.teknos.pokedex

import android.content.ClipData
import android.content.ClipDescription
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.DragShadowBuilder
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import cat.teknos.pokedex.api.PokemonFetchResults
import cat.teknos.pokedex.databinding.FragmentItemListBinding
import cat.teknos.pokedex.databinding.ItemListContentBinding
import cat.teknos.pokedex.placeholder.PlaceholderContent
import cat.teknos.pokedex.placeholder.PlaceholderContent.PlaceholderItem
import cat.teknos.pokedex.service.PokemonAPIService
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ItemListFragment : Fragment() {

    private var binding: FragmentItemListBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemListBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().serializeNulls().create()
                )
            )
            .build()

        val pokemonApiService = retrofit.create(
            PokemonAPIService::class.java
        )

        val call = pokemonApiService.pokemons
        call?.enqueue(object : Callback<PokemonFetchResults?> {
            override fun onResponse(
                call: Call<PokemonFetchResults?>,
                response: Response<PokemonFetchResults?>
            ) {
                if (response.isSuccessful) {
                    assert(response.body() != null)
                    response.body()!!.results
                } else {
                    Log.d("Error:", "Couldn't get list")
                }
            }

            override fun onFailure(call: Call<PokemonFetchResults?>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })

        val recyclerView = binding!!.itemList
        val itemDetailFragmentContainer = view.findViewById<View>(R.id.item_detail_nav_container)

        val onClickListener = View.OnClickListener { itemView: View ->
            val item = itemView.tag as PlaceholderItem
            val arguments = Bundle()
            arguments.putString(ItemDetailFragment.ARG_ITEM_ID, item.id)
            if (itemDetailFragmentContainer != null) {
                findNavController(itemDetailFragmentContainer)
                    .navigate(R.id.fragment_item_detail, arguments)
            } else {
                findNavController(itemView).navigate(R.id.show_item_detail, arguments)
            }
        }

        val onContextClickListener = View.OnContextClickListener { itemView: View ->
            val item = itemView.tag as PlaceholderItem
            Toast.makeText(
                itemView.context,
                "Context click of item " + item.id,
                Toast.LENGTH_SHORT
            ).show()
            true
        }
        setupRecyclerView(recyclerView, onClickListener, onContextClickListener)
    }

    private fun setupRecyclerView(
        recyclerView: RecyclerView,
        onClickListener: View.OnClickListener,
        onContextClickListener: View.OnContextClickListener
    ) {
        recyclerView.adapter = SimpleItemRecyclerViewAdapter(
            PlaceholderContent.ITEMS,
            onClickListener,
            onContextClickListener
        )
    }

    class SimpleItemRecyclerViewAdapter internal constructor(
        private val mValues: List<PlaceholderItem>,
        private val mOnClickListener: View.OnClickListener,
        private val mOnContextClickListener: View.OnContextClickListener
    ) : RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding =
                ItemListContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.mIdView.text = mValues[position].id
            holder.mContentView.text = mValues[position].content
            holder.itemView.tag = mValues[position]
            holder.itemView.setOnClickListener(mOnClickListener)
            holder.itemView.setOnContextClickListener(mOnContextClickListener)
            holder.itemView.setOnLongClickListener { v: View ->
                val clipItem = ClipData.Item(mValues[position].id)
                val dragData = ClipData(
                    (v.tag as PlaceholderItem).content,
                    arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN),
                    clipItem
                )
                v.startDragAndDrop(
                    dragData,
                    DragShadowBuilder(v),
                    null,
                    0
                )
                true
            }
        }

        override fun getItemCount(): Int {
            return mValues.size
        }

        class ViewHolder(binding: ItemListContentBinding) : RecyclerView.ViewHolder(binding.root) {
            val mIdView: TextView
            val mContentView: TextView

            init {
                mIdView = binding.idText
                mContentView = binding.content
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
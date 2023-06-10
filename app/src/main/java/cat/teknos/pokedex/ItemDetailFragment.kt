package cat.teknos.pokedex

import android.os.Bundle
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnDragListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import cat.teknos.pokedex.databinding.FragmentItemDetailBinding
import cat.teknos.pokedex.placeholder.PlaceholderContent
import cat.teknos.pokedex.placeholder.PlaceholderContent.PlaceholderItem
import com.google.android.material.appbar.CollapsingToolbarLayout

class ItemDetailFragment : Fragment() {

    private var mItem: PlaceholderItem? = null
    private var mToolbarLayout: CollapsingToolbarLayout? = null
    private var mTextView: TextView? = null
    private var binding: FragmentItemDetailBinding? = null

    private val dragListener = OnDragListener { _: View?, event: DragEvent ->
        if (event.action == DragEvent.ACTION_DROP) {
            val clipDataItem = event.clipData.getItemAt(0)
            mItem = PlaceholderContent.ITEM_MAP[clipDataItem.text.toString()]
            updateContent()
        }
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        assert(arguments != null)
        if (requireArguments().containsKey(ARG_ITEM_ID)) {
            mItem = PlaceholderContent.ITEM_MAP[requireArguments().getString(ARG_ITEM_ID)]
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemDetailBinding.inflate(inflater, container, false)
        val rootView: View = binding!!.root
        mToolbarLayout = rootView.findViewById(R.id.toolbar_layout)
        mTextView = binding!!.itemDetail
        updateContent()
        rootView.setOnDragListener(dragListener)
        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun updateContent() {
        if (mItem != null) {
            mTextView!!.text = mItem!!.details
            if (mToolbarLayout != null) {
                mToolbarLayout!!.title = mItem!!.content
            }
        }
    }

    companion object {
        const val ARG_ITEM_ID = "item_id"
    }
}
package com.csgames.mixparadise.ingredients

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.widget.DialogTitle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.csgames.mixparadise.R
import com.csgames.mixparadise.extensions.setImmersiveMode
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.view_ingredients_dialog.view.*

typealias IngredientSelectedListener = (
    id: String
) -> Unit




class MyAdapter(private val myDataset: Array<String>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var  title: TextView
        init {
            title =  view.findViewById<TextView>(R.id.title)
        }
    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyAdapter.MyViewHolder {
        // create a new view
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_ingredient_item, parent, false) as View
        // set the view's size, margins, paddings and layout parameters

        return MyViewHolder(textView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.title.text = myDataset[position]
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size
}

class IngredientsBottomSheetDialogFragment : BottomSheetDialogFragment() {

    companion object {
        const val INGREDIENTS_ID_TO_OUNCES_MAP_KEY = "INGREDIENTS_ID_TO_OUNCES_MAP_KEY"
    }


    private lateinit  var ingredientsView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private var ingredientSelectedListener: IngredientSelectedListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceSttea: Bundle?): View? {

        return inflater.inflate(R.layout.view_ingredients_dialog, container, false).also {
            setupDialogView(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewManager = GridLayoutManager(this.activity, 4)
        var myDataset = arrayOf("one","two","three")
        viewAdapter = MyAdapter(myDataset)

        ingredientsView = view.findViewById<RecyclerView>(R.id.ingredients).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun setupDialogView(dialogView: View) {
        dialogView.close.setOnClickListener {
            dismiss()
        }

    }

    fun setIngredientSelectedListener(ingredientSelectedListener: IngredientSelectedListener) {
        this.ingredientSelectedListener = ingredientSelectedListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?) = super.onCreateDialog(savedInstanceState).also {
        it.window?.setImmersiveMode()
    }

    override fun onStart() {
        super.onStart()

        dialog?.findViewById<View>(R.id.design_bottom_sheet)?.let { bottomSheet ->
            BottomSheetBehavior.from(bottomSheet).isHideable = false
            bottomSheet.layoutParams?.height = ViewGroup.LayoutParams.MATCH_PARENT
        }
    }
}
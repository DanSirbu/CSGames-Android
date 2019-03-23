package com.csgames.mixparadise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.csgames.mixparadise.api.Api
import com.csgames.mixparadise.api.IngredientsReturnModel
import com.csgames.mixparadise.extensions.*
import com.csgames.mixparadise.ingredients.IngredientsBottomSheetDialogFragment
import kotlinx.android.synthetic.main.view_blender_with_table.*
import com.csgames.mixparadise.result.ResultDialogFragment
import retrofit2.Call
import retrofit2.Callback


class MainActivity : AppCompatActivity() {

    companion object {
        private const val RESULT_FRAGMENT_TAG = "RESULT_FRAGMENT_TAG"
    }

    private val ingredientsDialog = IngredientsBottomSheetDialogFragment()
    private val resultDialog = ResultDialogFragment()
    private lateinit var blender: Blender

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupWaveView()
        setupStackView()
        setupSolidIngredientsWrapper()

        Api.drinkService.getIngredients().enqueue(object: Callback<IngredientsReturnModel> {
            override fun onFailure(call: Call<IngredientsReturnModel>, t: Throwable) {
                Log.d("ASD", "Failed")
            }

            override fun onResponse(
                call: Call<IngredientsReturnModel>,
                response: retrofit2.Response<IngredientsReturnModel>
            ) {
                var tag = "ASD"

                Log.d(tag, "Success: " + response.isSuccessful.toString())

                Log.d(tag, "Message: " + response.message())
                Log.d(tag, "Obj: " + response.body()?.ingredients)
            }

        })

        blender = Blender(wave, stackView, solidIngredientsContainer, 10, {
            addIngredientsButton.visibility = View.GONE
            serveButton.visibility = View.VISIBLE
        }) {
            showResultDialog()
        }

        setupListeners(blender, ingredientsDialog)
    }

    private fun showResultDialog() {
        if (resultDialog.isAdded) {
            return
        }

        resultDialog.show(supportFragmentManager, RESULT_FRAGMENT_TAG)
    }

    override fun onAttachFragment(fragment: Fragment?) {
        super.onAttachFragment(fragment)
        (fragment as? IngredientsBottomSheetDialogFragment)?.apply {
            fragment.setIngredientSelectedListener { id ->

            }
        }
    }

    // TODO: pass the juice
    private fun onJuiceSelected() {
        blender.addLiquid("orange", "#A66C1E", 0.5f)
    }

    // TODO: pass the drink
    private fun onDrinkSelected() {
        blender.addLiquid("pepsi", "#A66C1E", 0.5f)
    }

    // TODO: pass the ingredient
    private fun onIngredientSelected() {
        blender.addSolidIngredient()
    }

    // TODO: pass the alcohol
    private fun onAlcoholSelected() {
        blender.addLiquid("Four Loko", "#A66C1E", 0.5f)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            window.setImmersiveMode()
        }
    }
}

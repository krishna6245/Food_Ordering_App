package com.example.foodorderingapp.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.foodorderingapp.R
import com.example.foodorderingapp.adapters.HomeFragmentImageAdapter
import com.example.foodorderingapp.adapters.MenuItemAdapter
import com.example.foodorderingapp.databinding.FragmentHomeBinding
import com.example.foodorderingapp.fragment.bottomSheets.MenuBottomSheetFragment
import kotlin.math.abs

class HomeFragment : Fragment() {
    private lateinit var binding:FragmentHomeBinding

    private lateinit var homeFragmentViewPager : ViewPager2
    private lateinit var homeFragmentHandler: Handler
    private lateinit var homeFragmentImageList : ArrayList<Int>
    private lateinit var homeFragmentImageAdapter: HomeFragmentImageAdapter

    private lateinit var homeFragmentPopularItemAdapter: MenuItemAdapter
    private lateinit var homeFragmentFoodNames : List<String>
    private lateinit var homeFragmentFoodImages : List<Int>
    private lateinit var homeFragmentFoodPrices : List<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        init()
        return binding.root
    }

    private fun setLists(){
        homeFragmentViewPager = binding.homeFragmentImageScrollList
        homeFragmentHandler = Handler(Looper.myLooper()!!)
        homeFragmentImageList = ArrayList()
        homeFragmentImageList.add(R.drawable.dummy_image)
        homeFragmentImageList.add(R.drawable.dummy_image_1)
        homeFragmentImageList.add(R.drawable.dummy_image)
        homeFragmentImageList.add(R.drawable.dummy_image_1)
        homeFragmentImageList.add(R.drawable.dummy_image)
        homeFragmentImageList.add(R.drawable.dummy_image_1)
        homeFragmentImageList.add(R.drawable.dummy_image)
        homeFragmentImageList.add(R.drawable.dummy_image_1)

        homeFragmentImageAdapter = HomeFragmentImageAdapter(homeFragmentImageList,homeFragmentViewPager)

        homeFragmentViewPager.adapter = homeFragmentImageAdapter
        homeFragmentViewPager.offscreenPageLimit=5
        homeFragmentViewPager.clipToPadding=false
        homeFragmentViewPager.clipChildren=false
        homeFragmentViewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER


        homeFragmentFoodNames = listOf("Biryani","Burger","Pizza","Momos","Rolls","Fries","Sandwich","Muffins")
        homeFragmentFoodPrices = listOf(100,70,150,60,75,80,40,60)
        val a:Int = R.drawable.dummy_image
        val b:Int = R.drawable.dummy_image_1
        homeFragmentFoodImages = listOf(a,b,a,b,a,b,a,b)
    }
    private fun setAdapters(){
        homeFragmentPopularItemAdapter = MenuItemAdapter(homeFragmentFoodNames,homeFragmentFoodImages,homeFragmentFoodPrices)
        binding.homeFragmentPopularItemList.layoutManager = LinearLayoutManager(requireContext())
        binding.homeFragmentPopularItemList.adapter = homeFragmentPopularItemAdapter
    }
    private val runnable = Runnable{
        homeFragmentViewPager.currentItem = homeFragmentViewPager.currentItem+1;
    }
    private fun setTransformers(){
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer{page , position->
            val r=abs(position)
            page.scaleY = 1f - r * 0.2f
        }

        homeFragmentViewPager.setPageTransformer(transformer)
    }
    private fun setListeners(){
        homeFragmentViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                homeFragmentHandler.removeCallbacks(runnable)
                homeFragmentHandler.postDelayed(runnable,5000)
            }
        })

        binding.homeFragmentViewMenu.setOnClickListener{
            Toast.makeText(requireContext(),"Hello",Toast.LENGTH_SHORT).show()
        }

        binding.homeFragmentViewMenu.setOnClickListener{
            val bottomSheetDialog = MenuBottomSheetFragment()
            bottomSheetDialog.show(parentFragmentManager,"Tag")
        }
    }
    private fun init(){
        setLists()
        setAdapters()
        setTransformers()
        setListeners()
    }

}
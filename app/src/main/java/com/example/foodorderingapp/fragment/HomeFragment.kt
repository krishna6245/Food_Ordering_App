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
import kotlin.math.abs

class HomeFragment : Fragment() {
    private lateinit var binding:FragmentHomeBinding

    private lateinit var viewPager : ViewPager2
    private lateinit var handler: Handler
    private lateinit var imageList : ArrayList<Int>
    private lateinit var imageAdapter: HomeFragmentImageAdapter

    private lateinit var popularItemAdapter: MenuItemAdapter
    private lateinit var foodNames : List<String>
    private lateinit var foodImages : List<Int>
    private lateinit var foodPrices : List<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        init()
        setUpTransformer()

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable,5000)
            }
        })

        binding.homeFragmentViewMenu.setOnClickListener{
            Toast.makeText(requireContext(),"Hello",Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    private val runnable = Runnable{
        viewPager.currentItem = viewPager.currentItem+1;
    }
    private fun setUpTransformer(){
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer{page , position->
            val r=abs(position)
            page.scaleY = 1f - r * 0.2f
        }

        viewPager.setPageTransformer(transformer)
    }
    private fun init(){
        viewPager = binding.homeFragmentImageScrollList
        handler = Handler(Looper.myLooper()!!)
        imageList = ArrayList()
        imageList.add(R.drawable.dummy_image)
        imageList.add(R.drawable.dummy_image_1)
        imageList.add(R.drawable.dummy_image)
        imageList.add(R.drawable.dummy_image_1)
        imageList.add(R.drawable.dummy_image)
        imageList.add(R.drawable.dummy_image_1)
        imageList.add(R.drawable.dummy_image)
        imageList.add(R.drawable.dummy_image_1)

        imageAdapter = HomeFragmentImageAdapter(imageList,viewPager)

        viewPager.adapter = imageAdapter
        viewPager.offscreenPageLimit=5
        viewPager.clipToPadding=false
        viewPager.clipChildren=false
        viewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER


        foodNames = listOf("Biryani","Burger","Pizza","Momos","Rolls","Fries","Sandwich","Muffins")
        foodPrices = listOf(100,70,150,60,75,80,40,60)
        val a:Int = R.drawable.dummy_image
        val b:Int = R.drawable.dummy_image_1
        foodImages = listOf(a,b,a,b,a,b,a,b)

        popularItemAdapter = MenuItemAdapter(foodNames,foodImages,foodPrices)
        binding.homeFragmentPopularItemList.layoutManager = LinearLayoutManager(requireContext())
        binding.homeFragmentPopularItemList.adapter = popularItemAdapter
    }

}
package com.example.foodorderingapp.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.foodorderingapp.R
import com.example.foodorderingapp.adapters.HomeFragmentImageAdapter
import com.example.foodorderingapp.databinding.FragmentHomeBinding
import kotlin.math.abs

class HomeFragment : Fragment() {
    private lateinit var binding:FragmentHomeBinding

    private lateinit var viewPager : ViewPager2
    private lateinit var handler: Handler
    private lateinit var imageList : ArrayList<Int>
    private lateinit var adapter: HomeFragmentImageAdapter

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
                handler.postDelayed(runnable,2000)
            }
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        handler.postDelayed(runnable,2000)
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


        adapter = HomeFragmentImageAdapter(imageList,viewPager)

        viewPager.adapter = adapter
        viewPager.offscreenPageLimit=5
        viewPager.clipToPadding=false
        viewPager.clipChildren=false
        viewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
    }

}
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.foodorderingapp.R

class PageIndicator(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var count = 0
    private var selectedColor = Color.BLUE
    private var unselectedColor = Color.GRAY
    private var selectedSize = 10f
    private var unselectedSize = 6f
    private var space = 4f

    private val paintSelected = Paint().apply {
        color = selectedColor
        style = Paint.Style.FILL
    }

    private val paintUnselected = Paint().apply {
        color = unselectedColor
        style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val radius = resources.getDimension(R.dimen.page_indicator_size) / 2
        val width = (radius * 2 + space) * count

        for (i in 0 until count) {
            if (i == selectedPosition) {
                canvas.drawCircle(
                    width / count * i + radius + space / 2,
                    radius + space / 2,
                    radius,
                    paintSelected
                )
            } else {
                canvas.drawCircle(
                    width / count * i + radius + space / 2,
                    radius + space / 2,
                    radius - 2,
                    paintUnselected
                )
            }
        }
    }

    fun select(position: Int) {
        selectedPosition = position
        invalidate()
    }

    var selectedPosition = 0
        set(value) {
            if (value < 0 || value >= count) {
                throw IndexOutOfBoundsException("Selected position is out of bounds")
            }
            field = value
        }

    fun setCount(count: Int) {
        this.count = count
        requestLayout()
    }

    fun setSelectedColor(color: Int) {
        selectedColor = color
        paintSelected.color = color
        invalidate()
    }

    fun setUnselectedColor(color: Int) {
        unselectedColor = color
        paintUnselected.color = color
        invalidate()
    }

    fun setSelectedSize(size: Float) {
        selectedSize = size
        requestLayout()
    }

    fun setUnselectedSize(size: Float) {
        unselectedSize = size
        requestLayout()
    }

    fun setSpace(space: Float) {
        this.space = space
        requestLayout()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val width = (radius * 2 + space) * count
        setMeasuredDimension(width.toInt(), (radius * 2 + space).toInt())
    }

    private companion object {
        const val radius = 6
    }
}
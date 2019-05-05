import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.reflectit.R
import com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemAdapter
import com.h6ah4i.android.widget.advrecyclerview.draggable.ItemDraggableRange
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractDraggableItemViewHolder
import com.example.reflectit.ui.data.services.Widget
import com.example.reflectit.ui.data.services.WidgetCategory
import com.example.reflectit.ui.extensions.GlideApp


class WidgetGridAdapter(val selectedWidgets: MutableList<Widget>, val context: Context?) :
    RecyclerView.Adapter<MyAdapter.Companion.MyViewHolder>(),
    DraggableItemAdapter<MyAdapter.Companion.MyViewHolder> {

    override fun getItemId(position: Int): Long {
        return selectedWidgets[position].id.toLong()
    }

    override fun onGetItemDraggableRange(holder: MyAdapter.Companion.MyViewHolder, position: Int): ItemDraggableRange? {
        return null
    }

    override fun onCheckCanStartDrag(holder: MyAdapter.Companion.MyViewHolder, position: Int, x: Int, y: Int): Boolean {
        val itemView = holder.itemView
//        val dragHandle = holder.dragHandle

        val handleWidth = itemView.width
        val handleHeight = itemView.height
        val handleLeft = itemView.left
        val handleTop = itemView.top

        return x >= handleLeft && x < handleLeft + handleWidth &&
                y >= handleTop && y < handleTop + handleHeight
    }

    override fun onItemDragStarted(position: Int) {
        notifyDataSetChanged()
    }

    override fun onMoveItem(fromPosition: Int, toPosition: Int) {
        val widget = selectedWidgets.removeAt(fromPosition)
        selectedWidgets.add(toPosition, widget)
    }

    override fun onCheckCanDrop(draggingPosition: Int, dropPosition: Int): Boolean {
        return true
    }

    override fun onItemDragFinished(fromPosition: Int, toPosition: Int, result: Boolean) {
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.Companion.MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.widgets_position_row, parent, false)
        return MyAdapter.Companion.MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return selectedWidgets.size
    }

    override fun onBindViewHolder(holder: MyAdapter.Companion.MyViewHolder, position: Int) {
        if (selectedWidgets[position].category != WidgetCategory.Placeholder) {
            GlideApp
                .with(holder.imageView.context)
                .load(selectedWidgets[position].imageUrl)
                .fitCenter()
                .placeholder(R.drawable.mirror_image)
                .into(holder.imageView)
        }
    }

    init {
        setHasStableIds(true)
    }
}

class MyAdapter {
    companion object {
        class MyViewHolder(v: View) : AbstractDraggableItemViewHolder(v) {
            val imageView: ImageView
//            val dragHandle: View

            init {
                imageView = v.findViewById(R.id.widgetImageView)
//                dragHandle = v.findViewById(R.id.drag_handle)
            }

        }
    }
}
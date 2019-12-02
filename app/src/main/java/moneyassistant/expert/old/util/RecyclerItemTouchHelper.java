package moneyassistant.expert.old.util;

import android.graphics.Canvas;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import moneyassistant.expert.ui.account.adapter.AccountAdapter;
import moneyassistant.expert.ui.category.adapter.CategoryAdapter;

public class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {

    private RecyclerItemTouchHelperListener listener;

    private AccountAdapter accountAdapter;
    private CategoryAdapter categoryAdapter;

    public RecyclerItemTouchHelper(int dragDirs, int swipeDirs,
                                   RecyclerItemTouchHelperListener listener) {
        super(dragDirs, swipeDirs);
        this.listener = listener;
    }

    public void setAccountAdapter(AccountAdapter accountAdapter) {
        this.accountAdapter = accountAdapter;
    }

    public void setCategoryAdapter(CategoryAdapter categoryAdapter) {
        this.categoryAdapter = categoryAdapter;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView,
                          @NonNull RecyclerView.ViewHolder viewHolder,
                          @NonNull RecyclerView.ViewHolder target) {
        return true;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (viewHolder != null) {
            View foregroundView = null;
            if (accountAdapter != null) {
//                foregroundView = ((AccountAdapter.AccountViewHolder) viewHolder).viewForeground;
            } else if (categoryAdapter != null) {
//                foregroundView = ((CategoryAdapter.CategoryViewHolder) viewHolder).viewForeground;
            }
            if (foregroundView != null) {
                getDefaultUIUtil().onSelected(foregroundView);
            }
        }
    }

    @Override
    public void onChildDrawOver(@NonNull Canvas c,
                                @NonNull RecyclerView recyclerView,
                                RecyclerView.ViewHolder viewHolder,
                                float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View foregroundView = null;
        if (accountAdapter != null) {
//            foregroundView = ((AccountAdapter.AccountViewHolder) viewHolder).viewForeground;
        } else if (categoryAdapter != null) {
//            foregroundView = ((CategoryAdapter.CategoryViewHolder) viewHolder).viewForeground;
        }
        if (foregroundView != null) {
            getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY,
                    actionState, isCurrentlyActive);
        }
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView,
                          @NonNull RecyclerView.ViewHolder viewHolder) {
        View foregroundView = null;
        if (accountAdapter != null) {
//            foregroundView = ((AccountAdapter.AccountViewHolder) viewHolder).viewForeground;
        } else if (categoryAdapter != null) {
//            foregroundView = ((CategoryAdapter.CategoryViewHolder) viewHolder).viewForeground;
        }
        if (foregroundView != null) {
            getDefaultUIUtil().clearView(foregroundView);
        }
    }

    @Override
    public void onChildDraw(@NonNull Canvas c,
                            @NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View foregroundView = null;
        if (accountAdapter != null) {
//            foregroundView = ((AccountAdapter.AccountViewHolder) viewHolder).viewForeground;
        } else if (categoryAdapter != null) {
//            foregroundView = ((CategoryAdapter.CategoryViewHolder) viewHolder).viewForeground;
        }
        if (foregroundView != null) {
            getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY,
                    actionState, isCurrentlyActive);
        }
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        listener.onSwiped(viewHolder, direction, viewHolder.getAdapterPosition());
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    public interface RecyclerItemTouchHelperListener {
        void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
    }
}

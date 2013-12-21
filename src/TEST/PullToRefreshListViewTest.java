package TEST;

import android.content.Context;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

public class PullToRefreshListViewTest extends ListView implements
		OnScrollListener {

	public PullToRefreshListViewTest(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {
		// TODO Auto-generated method stub

	}

}

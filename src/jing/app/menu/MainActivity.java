package jing.app.menu;

import java.util.HashSet;
import java.util.Set;

import android.app.Activity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener, OnMenuItemClickListener {//implements OnItemLongClickListener {
	private ListView mList;
	private ArrayAdapter mAdapter;
	
	private Button mPopup;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mList = (ListView) findViewById(R.id.listView1);
		
		String[] contents = new String[20];
		for (int i=0; i< contents.length; i++) {
			contents[i] = "Item " + i;
		}
		mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, contents);
		mList.setAdapter(mAdapter);
		//mList.setOnItemLongClickListener(this);
		
		// For multi-choiceiChoiceModeListener);
		mList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		mList.setMultiChoiceModeListener(mMultiChoiceModeListener);
		//registerForContextMenu(mList);
		
		mPopup = (Button) findViewById(R.id.popup);
		mPopup.setOnClickListener(this);
		
	}
	
	private ActionMode mActionMode;

	private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
		
		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			return false;
		}
		
		@Override
		public void onDestroyActionMode(ActionMode mode) {
			mode.finish();
		}
		
		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.context_menu, menu);
			return true;
		}
		
		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			Toast.makeText(MainActivity.this, item.getTitle() + " selected.", Toast.LENGTH_SHORT).show();
			mode.finish();
			return true;
		}
	};
	
	private Set<Integer> mSelected = new HashSet<Integer>();
	
	private MultiChoiceModeListener mMultiChoiceModeListener = new MultiChoiceModeListener() {
		
		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			return false;
		}
		
		@Override
		public void onDestroyActionMode(ActionMode mode) {
		}
		
		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			mSelected.clear();
			MenuInflater inflater = mode.getMenuInflater();
			inflater.inflate(R.menu.context_menu, menu);
			return true;
		}
		
		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			mode.finish();
			Toast.makeText(MainActivity.this, String.valueOf(mSelected.size()) + " selected.", Toast.LENGTH_SHORT).show();
			return true;
		}
		
		@Override
		public void onItemCheckedStateChanged(ActionMode mode, int position,
				long id, boolean checked) {
			updateSelected(position, checked);
			// mList.setItemChecked(position, checked);
		}
	};
	
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.context_menu, menu);
	}

	protected void updateSelected(int position, boolean checked) {
		if (checked) {
			mSelected.add(position);
		} else {
			mSelected.remove(position);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		Toast.makeText(this, item.getTitle() + " selected.", Toast.LENGTH_SHORT).show();
		return true;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.popup) {
			showPopup(v);
		}
	}

	private void showPopup(View v) {
		PopupMenu popup = new PopupMenu(this, v);
		popup.setOnMenuItemClickListener(this);
		MenuInflater inflater = popup.getMenuInflater();
		inflater.inflate(R.menu.popup_menu, popup.getMenu());
		popup.show();
	}

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		Toast.makeText(MainActivity.this, item.getTitle() + " selected.", Toast.LENGTH_SHORT).show();
		return true;
	}
	
	

//	@Override
//	public boolean onItemLongClick(AdapterView<?> parent, View view,
//			int position, long id) {
//		if (mActionMode != null) {
//			return false;
//		}
//		
//		mActionMode = startActionMode(mActionModeCallback);
//		mActionMode.setTitle("ÇëÑ¡Ôñ²Ù×÷");
//		view.setSelected(true);
//		return true;
//	}
	
}

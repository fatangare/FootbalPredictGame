package com.footballpredictgame.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.footballpredictgame.R;
import com.footballpredictgame.adapter.DrawerListAdapter;
import com.footballpredictgame.fragment.BetsFragment;
import com.footballpredictgame.fragment.BetsFragment.OnBetsFragmentInteractionListener;
import com.footballpredictgame.fragment.ChangePasswordFragment;
import com.footballpredictgame.fragment.ChangePasswordFragment.OnChangePasswordFragmentInteractionListener;
import com.footballpredictgame.fragment.GroupDialogFragment;
import com.footballpredictgame.fragment.GroupDialogFragment.GroupDialogFragmentListener;
import com.footballpredictgame.fragment.GroupsFragment;
import com.footballpredictgame.fragment.HistoryFragment;
import com.footballpredictgame.fragment.ProfileFragment;
import com.footballpredictgame.fragment.ProfileFragment.OnProfileFragmentInteractionListener;
import com.footballpredictgame.utiity.Constant;

public class MainActivity extends FragmentActivity implements
		OnProfileFragmentInteractionListener,
		OnBetsFragmentInteractionListener,
		OnChangePasswordFragmentInteractionListener,
		GroupDialogFragmentListener, OnItemClickListener {

	private static final int BETHEADERINDEX = 1;
	private int mGroupHeaderIndex;

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;

	private DrawerListAdapter mAdapter;
	private List<String> mDrawerListItems;
	private List<String> mDrawerListItemKeys;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mGroupHeaderIndex = -1;
		mDrawerListItems = new ArrayList<String>();
		mDrawerListItemKeys = new ArrayList<String>();
		mTitle = mDrawerTitle = getTitle();
		createDrawerListItemsArray();
		mAdapter = new DrawerListAdapter(this, mDrawerListItems,
				mDrawerListItemKeys);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		// set a custom shadow that overlays the main content when the drawer
		// opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		// set up the drawer's list view with items and click listener
		mDrawerList.setAdapter(mAdapter);
		mDrawerList.setOnItemClickListener(this);

		// enable ActionBar app icon to behave as action to toggle nav drawer
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description for accessibility */
		R.string.drawer_close /* "close drawer" description for accessibility */
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			selectItem(BETHEADERINDEX + 1);
		}
		// mDrawerList.setVisibility(View.GONE);
	}

	/* Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content
		// view
		// boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		// menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action buttons
		switch (item.getItemId()) {
		/*
		 * case R.id.action_websearch: // create intent to perform web search
		 * for this planet Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
		 * intent.putExtra(SearchManager.QUERY, getActionBar().getTitle()); //
		 * catch event that there's no activity to handle intent if
		 * (intent.resolveActivity(getPackageManager()) != null) {
		 * startActivity(intent); } else { Toast.makeText(this,
		 * R.string.app_not_available, Toast.LENGTH_LONG).show(); } return true;
		 */
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		selectItem(position);
	}

	private void selectItem(int position) {
		if (mDrawerListItemKeys.get(position) == DrawerListAdapter.BET_ITEM
				|| mDrawerListItemKeys.get(position) == DrawerListAdapter.GROUP_ITEM
				|| mDrawerListItemKeys.get(position) == DrawerListAdapter.PROFILE_ITEM) {

			if (mDrawerListItems.get(position).compareTo(
					getString(R.string.drawer_item_addgroup)) == 0) {

				// update selected item and title, then close the drawer
				mDrawerList.setItemChecked(position, true);
				mDrawerLayout.closeDrawer(mDrawerList);

				FragmentManager fm = getSupportFragmentManager();
				GroupDialogFragment groupDialog = new GroupDialogFragment();
				groupDialog.show(fm, Constant.TAG);
			}

			// update the main content by replacing fragments
			Fragment fragment = getFragment(position);

			if (fragment != null) {
				FragmentTransaction fragmentTransaction = getSupportFragmentManager()
						.beginTransaction();
				fragmentTransaction.replace(R.id.content_frame, fragment);
				if (!(fragment instanceof BetsFragment)) {
					// it will add screen to home screen when back is pressed
					fragmentTransaction.addToBackStack(null);
				}
				fragmentTransaction.commit();
				// update selected item and title, then close the drawer
				mDrawerList.setItemChecked(position, true);
				mDrawerLayout.closeDrawer(mDrawerList);
			}
		}
		// if(mDrawerListItems.get(position).equals(getString(R.string.drawer_item_logout)))

	}

	private Fragment getFragment(int position) {
		Fragment fr = null;
		if (mDrawerListItemKeys == null || mDrawerListItemKeys.isEmpty())
			return new BetsFragment();
		;
		if (mDrawerListItemKeys.get(position)
				.equals(DrawerListAdapter.BET_ITEM)) {
			if (mDrawerListItems.get(position).equals(
					getString(R.string.drawer_item_history))) {
				return new HistoryFragment();

			} else if (mDrawerListItems.get(position).equals(
					getString(R.string.drawer_item_rules))) {

			} else {
				mTitle = getString(R.string.drawer_item_bets);
				getActionBar().setTitle(mTitle);
				return new BetsFragment();
			}
		}

		if (mDrawerListItemKeys.get(position).equals(
				DrawerListAdapter.GROUP_ITEM)) {
			if (mDrawerListItems.get(position).equals(
					getString(R.string.drawer_item_viewallgroups))) {
				return new GroupsFragment();
			}
		}

		if (mDrawerListItemKeys.get(position).equals(
				DrawerListAdapter.PROFILE_ITEM)) {
			if (mDrawerListItems.get(position).equals(
					getString(R.string.drawer_item_editprofile))) {
				mTitle = getString(R.string.drawer_item_editprofile);
				getActionBar().setTitle(mTitle);
				return new ProfileFragment();
			} else if (mDrawerListItems.get(position).equals(
					getString(R.string.drawer_item_changepwd))) {
				mTitle = getString(R.string.drawer_item_changepwd);
				getActionBar().setTitle(mTitle);
				return new ChangePasswordFragment();
			}
		}

		return fr;
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void onFragmentInteraction(Uri uri) {
		// TODO Auto-generated method stub

	}

	private void createDrawerListItemsArray() {
		mDrawerListItems.clear();
		mDrawerListItemKeys.clear();

		String title = getString(R.string.drawer_item_hello);
		mDrawerListItemKeys.add(DrawerListAdapter.TITLE);
		mDrawerListItems.add(title);

		mDrawerListItemKeys.add(DrawerListAdapter.BET_HEADER);
		mDrawerListItems.add(getString(R.string.drawer_item_bets));
		mDrawerListItemKeys.add(DrawerListAdapter.BET_ITEM);
		mDrawerListItems.add("MI vs CSK");
		mDrawerListItemKeys.add(DrawerListAdapter.BET_ITEM);
		mDrawerListItems.add("RR vs DD");
		mDrawerListItemKeys.add(DrawerListAdapter.BET_ITEM);
		mDrawerListItems.add(getString(R.string.drawer_item_history));
		mDrawerListItemKeys.add(DrawerListAdapter.BET_ITEM);
		mDrawerListItems.add(getString(R.string.drawer_item_rules));

		mDrawerListItemKeys.add(DrawerListAdapter.GROUP_HEADER);
		mDrawerListItems.add(getString(R.string.drawer_item_groups));
		mGroupHeaderIndex = mDrawerListItemKeys.size() - 1;
		mDrawerListItemKeys.add(DrawerListAdapter.GROUP_ITEM);
		mDrawerListItems.add(getString(R.string.drawer_item_addgroup));
		mDrawerListItemKeys.add(DrawerListAdapter.GROUP_ITEM);
		mDrawerListItems.add(getString(R.string.drawer_item_viewallgroups));

		mDrawerListItemKeys.add(DrawerListAdapter.PROFILE_HEADER);
		mDrawerListItems.add(getString(R.string.drawer_item_profile));
		mDrawerListItemKeys.add(DrawerListAdapter.PROFILE_ITEM);
		mDrawerListItems.add(getString(R.string.drawer_item_editprofile));
		mDrawerListItemKeys.add(DrawerListAdapter.PROFILE_ITEM);
		mDrawerListItems.add(getString(R.string.drawer_item_changepwd));
		mDrawerListItemKeys.add(DrawerListAdapter.PROFILE_ITEM);
		mDrawerListItems.add(getString(R.string.drawer_item_logout));

	}

	@Override
	public void onDismissGroupDialog(String groupId) {
		Toast.makeText(this, groupId, Toast.LENGTH_LONG).show();

	}
}

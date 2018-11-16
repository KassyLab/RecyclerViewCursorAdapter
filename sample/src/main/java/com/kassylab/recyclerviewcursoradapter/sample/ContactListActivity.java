/*
 * Copyright (C) 2017  KassyLab
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kassylab.recyclerviewcursoradapter.sample;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.kassylab.recyclerviewcursoradapter.RecyclerViewCursorAdapter;

/**
 * An activity representing a list of Contacts. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ContactDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ContactListActivity extends AppCompatActivity implements
		LoaderManager.LoaderCallbacks<Cursor>, RecyclerViewCursorAdapter.OnItemInteractionListener {
	
	private static final String[] PROJECTION = {
			ContactsContract.Contacts._ID,
			ContactsContract.Contacts.LOOKUP_KEY,
			ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
	};
	
	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;
	private RecyclerViewCursorAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_list);
		
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		toolbar.setTitle(getTitle());
		
		FloatingActionButton fab = findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
						.setAction("Action", null).show();
			}
		});
		
		if (findViewById(R.id.contact_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-w900dp).
			// If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;
		}
		
		RecyclerView recyclerView = findViewById(R.id.contact_list);
		assert recyclerView != null;
		setupRecyclerView(recyclerView);
	}
	
	@Override
	protected void onPostCreate(@Nullable Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		
		getSupportLoaderManager().initLoader(0, null, this);
	}
	
	private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		mAdapter = new ContactRecyclerViewCursorAdapter();
		mAdapter.setOnItemInteractionListener(this);
		recyclerView.setAdapter(mAdapter);
	}
	
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		return new CursorLoader(
				this,
				ContactsContract.Contacts.CONTENT_URI,
				PROJECTION,
				null,
				null,
				ContactsContract.Contacts.DISPLAY_NAME + " ASC"
		);
	}
	
	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		mAdapter.swapCursor(data);
	}
	
	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		mAdapter.swapCursor(null);
	}
	
	@Override
	public void onItemSelected(Uri uri, int position) {
		if (mTwoPane) {
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.contact_detail_container, ContactDetailFragment.newInstance(uri))
					.commit();
		} else {
			startActivity(ContactDetailActivity.newIntent(this, uri));
		}
	}
}

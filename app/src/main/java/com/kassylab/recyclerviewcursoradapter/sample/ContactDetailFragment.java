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
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A fragment representing a single Contact detail screen.
 * This fragment is either contained in a {@link ContactListActivity}
 * in two-pane mode (on tablets) or a {@link ContactDetailActivity}
 * on handsets.
 */
public class ContactDetailFragment extends Fragment implements
		LoaderManager.LoaderCallbacks<Cursor> {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	private static final String ARG_ITEM_URI =
			ContactDetailFragment.class.getCanonicalName() + ".args.ITEM_URI";
	
	/**
	 * The dummy content this fragment is presenting.
	 */
	private Uri mItem;
	private TextView contactDetail;
	
	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public ContactDetailFragment() {
	}
	
	public static Fragment newInstance(Uri itemUri) {
		Fragment fragment = new ContactDetailFragment();
		Bundle args = new Bundle();
		args.putParcelable(ARG_ITEM_URI, itemUri);
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (getArguments().containsKey(ARG_ITEM_URI)) {
			// Load the dummy content specified by the fragment
			// arguments. In a real-world scenario, use a Loader
			// to load content from a content provider.
			mItem = getArguments().getParcelable(ARG_ITEM_URI);
			
			/*CollapsingToolbarLayout appBarLayout = getActivity().findViewById(R.id.toolbar_layout);
			if (appBarLayout != null) {
				appBarLayout.setTitle(mItem.content);
			}*/
		}
	}
	
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		if (mItem != null) {
			getActivity().getSupportLoaderManager().initLoader(0, null, this);
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.contact_detail, container, false);
		
		contactDetail = rootView.findViewById(R.id.contact_detail);
		
		return rootView;
	}
	
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		return new CursorLoader(
				getContext(),
				mItem,
				null,
				null,
				null,
				ContactsContract.Contacts.DISPLAY_NAME + " ASC"
		);
	}
	
	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		if (data != null && data.moveToFirst()) {
			int id = data.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
			if (id != -1) contactDetail.setText(data.getString(id));
		}
	}
	
	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
	
	}
}

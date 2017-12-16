# RecyclerViewCursorAdapter

Simplified CursorAdapter designed for RecyclerView.

[ ![Download](https://api.bintray.com/packages/kassylab/android/RecyclerViewCursorAdapter/images/download.svg?version=26.1.3) ](https://bintray.com/kassylab/android/RecyclerViewCursorAdapter/26.1.3/link)

## Installation

- Gradle 3.x:

		dependencies {
			implementation 'com.kassylab:recyclerview-cursor-adapter:26.1.3'
		}

- Gradle 2.x:

		dependencies {
			compile 'com.kassylab:recyclerview-cursor-adapter:26.1.3'
		}

## Usage

- Create `RecyclerViewCursorAdaptor` inherited class like this:

        public class MyRecyclerViewCursorAdapter
                extends RecyclerViewCursorAdapter<MyRecyclerViewCursorAdapter.ViewHolder> {
            
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item, parent, false);
                return new ViewHolder(view);
            }
            
            class ViewHolder extends RecyclerViewCursorAdapter.ViewHolder {
                
                final TextView mContentView;
                
                ViewHolder(View view) {
                    super(view);
                    mContentView = view.findViewById(R.id.content);
                }
                
                protected void bind(Cursor cursor) {
                    int mDisplayNameColumn = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY);
                    if (mDisplayNameColumn != -1) {
                        mContentView.setText(cursor.getString(mDisplayNameColumn));
                    }
                }
            }
        }

- Set adapter of `RecyclerView`: 

        RecyclerView recyclerView;
        ...
        recyclerView.setAdapter(new MyRecyclerViewCursorAdapter());

### For use OnItemInteractionListener

- Override method `onTouch(Uri, int)` of `RecyclerViewCursorAdapter.ViewHolder` class like this:

        @Override
        protected void onTouch(Uri itemUri, int position) {
            OnItemInteractionListener listener = getOnItemInteractionListener();
            if (listener != null) {
                listener.onItemSelected(itemUri, position);
            }
        }
        
- Set `itemUri` field of `RecyclerViewCursorAdapter.ViewHolder` class in `bind(Cursor)` method

- Set itemInteractionListener of `RecyclerView`:

        RecyclerViewCursorAdapter mAdapter = new ContactRecyclerViewCursorAdapter();
        mAdapter.setOnItemInteractionListener(new RecyclerViewCursorAdapter.OnItemInteractionListener() {
            @Override
            public void onItemSelected(Uri uri, int position) {
                //Do something
            }
        });
        recyclerView.setAdapter(mAdapter);


## Licence

	Copyright © 2016 KassyLab

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

	http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or 
	implied.
	See the License for the specific language governing permissions and
	limitations under the License.

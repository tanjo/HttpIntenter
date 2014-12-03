package in.tanjo.httpintenter;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import in.tanjo.httpintenter.model.UrlContent;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class UrlItemFragment extends Fragment implements AbsListView.OnItemClickListener {

  private OnFragmentInteractionListener mListener;

  /**
   * The fragment's ListView/GridView.
   */
  private AbsListView mListView;

  private UrlContent mUrlContent;

  /**
   * The Adapter which will be used to populate the ListView/GridView with
   * Views.
   */
  private ListAdapter mAdapter;

  // TODO: Rename and change types of parameters
  public static UrlItemFragment newInstance() {
    UrlItemFragment fragment = new UrlItemFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
  }

  /**
   * Mandatory empty constructor for the fragment manager to instantiate the
   * fragment (e.g. upon screen orientation changes).
   */
  public UrlItemFragment() {
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mUrlContent = new UrlContent();

    mAdapter = new ArrayAdapter<UrlContent.UrlItem>(getActivity(),
        android.R.layout.simple_list_item_1, android.R.id.text1, mUrlContent.getUrlItems());
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_urlitem, container, false);

    // Set the adapter
    mListView = (AbsListView) view.findViewById(android.R.id.list);
    ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

    // Set OnItemClickListener so we can be notified on item clicks
    mListView.setOnItemClickListener(this);

    return view;
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    try {
      mListener = (OnFragmentInteractionListener) activity;
    } catch (ClassCastException e) {
      throw new ClassCastException(activity.toString()
          + " must implement OnFragmentInteractionListener");
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    if (null != mListener) {
      // Notify the active callbacks interface (the activity, if the
      // fragment is attached to one) that an item has been selected.
      mListener.onFragmentInteraction(
          mUrlContent.getUrlItems().get(position).url,
          mUrlContent.getUrlItems().get(position).title);
    }
  }

  /**
   * The default content for this Fragment has a TextView that is shown when
   * the list is empty. If you would like to change the text, call this method
   * to supply the text it should use.
   */
  public void setEmptyText(CharSequence emptyText) {
    View emptyView = mListView.getEmptyView();

    if (emptyView instanceof TextView) {
      ((TextView) emptyView).setText(emptyText);
    }
  }

  /**
   * This interface must be implemented by activities that contain this
   * fragment to allow an interaction in this fragment to be communicated
   * to the activity and potentially other fragments contained in that
   * activity.
   * <p/>
   * See the Android Training lesson <a href=
   * "http://developer.android.com/training/basics/fragments/communicating.html"
   * >Communicating with Other Fragments</a> for more information.
   */
  public interface OnFragmentInteractionListener {
    public void onFragmentInteraction(String url, String text);
  }

}

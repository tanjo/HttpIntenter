package in.tanjo.httpintenter.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.tanjo.httpintenter.R;

public class HttpIntenterFragment extends Fragment {

  public static HttpIntenterFragment newInstance(String param1, String param2) {
    HttpIntenterFragment fragment = new HttpIntenterFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
  }

  public HttpIntenterFragment() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_http_intenter, container, false);
  }
}

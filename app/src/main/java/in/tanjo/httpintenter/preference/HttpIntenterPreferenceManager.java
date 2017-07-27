package in.tanjo.httpintenter.preference;

import com.google.gson.reflect.TypeToken;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import in.tanjo.httpintenter.model.ShareDataModel;
import in.tanjo.httpintenter.util.GsonUtils;

public class HttpIntenterPreferenceManager extends SharedPreferenceManager {

  private static final String KEY_SHARE_DATA_MODELS = "share_data_models";

  public HttpIntenterPreferenceManager(@NonNull Context context) {
    super(context, "httpintenter");
  }

  public void saveShareModels(@NonNull List<ShareDataModel> shareDataModels) {
    saveString(KEY_SHARE_DATA_MODELS, GsonUtils.getGson().toJson(shareDataModels));
  }

  public List<ShareDataModel> getShareDataModels() {
    try {
      // @formatter:off
      return GsonUtils.getGson().fromJson(getString(KEY_SHARE_DATA_MODELS), new TypeToken<List<ShareDataModel>>() {}.getType());
      // @formatter:on
    } catch (Exception e) {
      return new ArrayList<>();
    }
  }

  public void clear() {
    super.deleteAll();
  }
}

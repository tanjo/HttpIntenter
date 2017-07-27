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
    List<ShareDataModel> shareDataModels = null;
    try {
      shareDataModels = GsonUtils.getGson().fromJson(getString(KEY_SHARE_DATA_MODELS), new TypeToken<List<ShareDataModel>>() {
      }.getType());
    } catch (Exception ignored) {
    }
    if (shareDataModels == null) {
      return new ArrayList<>();
    }
    return shareDataModels;
  }

  public void clear() {
    super.deleteAll();
  }
}

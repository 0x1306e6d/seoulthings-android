package migong.seoulthings.ui.search;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import org.joda.time.DateTime;

public final class SearchResult {

  @NonNull
  private final String mScope;

  @NonNull
  private final String mId;

  @NonNull
  private final String mTitle;

  @NonNull
  private final String mContents;

  @Nullable
  private final DateTime mUpdatedAt;

  public SearchResult(@NonNull String scope, @NonNull String id, @NonNull String title,
      @NonNull String contents, @Nullable DateTime updatedAt) {
    this.mScope = scope;
    this.mId = id;
    this.mTitle = title;
    this.mContents = contents;
    this.mUpdatedAt = updatedAt;
  }

  @NonNull
  public String getScope() {
    return mScope;
  }

  @NonNull
  public String getId() {
    return mId;
  }

  @NonNull
  public String getTitle() {
    return mTitle;
  }

  @NonNull
  public String getContents() {
    return mContents;
  }

  @Nullable
  public DateTime getUpdatedAt() {
    return mUpdatedAt;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("SearchResult{");
    sb.append("scope='").append(mScope).append('\'');
    sb.append(", id='").append(mId).append('\'');
    sb.append(", title='").append(mTitle).append('\'');
    sb.append(", contents='").append(mContents).append('\'');
    sb.append(", updatedAt=").append(mUpdatedAt);
    sb.append('}');
    return sb.toString();
  }
}

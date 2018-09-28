package migong.seoulthings.ui.profile.modify;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import com.makeramen.roundedimageview.RoundedImageView;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import migong.seoulthings.R;

public class ModifyProfileActivity extends AppCompatActivity implements ModifyProfileView {

  private static final int REQUEST_IMAGE_CAPTURE = 0x00000001;

  private Button mCompleteButton;
  private ContentLoadingProgressBar mCompleteProgressBar;
  private RoundedImageView mPhotoImage;
  private Button mChangePhotoButton;

  private ModifyProfilePresenter mPresenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.modify_profile_activity);

    setupAppBar();
    setupPhoto();

    mPresenter = new ModifyProfilePresenter(this);
    mPresenter.onCreate(savedInstanceState);
  }

  @Override
  protected void onResume() {
    super.onResume();
    mPresenter.onResume();
  }

  @Override
  protected void onPause() {
    super.onPause();
    mPresenter.onPause();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mPresenter.onDestroy();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    switch (requestCode) {
      case REQUEST_IMAGE_CAPTURE:
        if (resultCode == RESULT_OK) {
          mPresenter.onRequestTakePhotoSuccess(data);
        }
        break;
      default:
        super.onActivityResult(requestCode, resultCode, data);
        break;
    }
  }

  @Override
  public void changePhoto(Uri photoUri) {
    Picasso.get()
        .load(photoUri)
        .fit()
        .transform(new RoundedTransformationBuilder()
            .borderColor(R.color.colorStroke)
            .borderWidthDp(1.0f)
            .oval(true)
            .build())
        .placeholder(R.drawable.ic_person_black_48)
        .into(mPhotoImage);
  }

  @Override
  public void startUpdateProfile() {
    mCompleteButton.setVisibility(View.INVISIBLE);
    mCompleteProgressBar.show();
  }

  @Override
  public void finishUpdateProfile() {
    mCompleteButton.setVisibility(View.VISIBLE);
    mCompleteProgressBar.hide();
  }

  @Override
  public void startTakePhotoIntent() {
    Intent takePhotoIntent = new Intent(Intent.ACTION_PICK, Media.EXTERNAL_CONTENT_URI);
    if (takePhotoIntent.resolveActivity(getPackageManager()) != null) {
      startActivityForResult(takePhotoIntent, REQUEST_IMAGE_CAPTURE);
    }
  }

  private void setupAppBar() {
    ImageButton backButton = findViewById(R.id.modify_profile_back_button);
    backButton.setOnClickListener(v -> onBackPressed());

    mCompleteButton = findViewById(R.id.modify_profile_complete_button);
    mCompleteButton.setOnClickListener(v -> mPresenter.onCompleteButtonClicked());
    mCompleteProgressBar = findViewById(R.id.modify_profile_complete_progress_bar);
    mCompleteProgressBar.hide();
  }

  private void setupPhoto() {
    mPhotoImage = findViewById(R.id.modify_profile_photo);

    mChangePhotoButton = findViewById(R.id.modify_profile_change_photo_button);
    mChangePhotoButton.setOnClickListener(v -> mPresenter.onChangePhotoButtonClicked());
  }
}

package com.example.lin_sir.helper.Activity.activitys;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.SignUpCallback;
import com.example.lin_sir.helper.Activity.common.BaseActivity;
import com.example.lin_sir.helper.Activity.utils.FileUtil;
import com.example.lin_sir.helper.Activity.utils.ImageUtil;
import com.example.lin_sir.helper.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;

/**
 * Created by lin_sir on 2016/2/12.
 */
public class MeActivity extends BaseActivity
{


    private SimpleDraweeView sdvAvatar;
    private PopupWindow popupWindow;
    private EditText e1;
    private TextView t1, t2, t3;
    final AVUser user = AVUser.getCurrentUser();
    private RelativeLayout r1, r2;
    private static final String IMAGE_NAME = "user_avatar.jpg";
    private static final int GALLERY_REQUEST = 102;
    private static final int GALLERY_KITKAT_REQUEST = 103;
    private static final int CAMERA_REQUEST = 104;
    private static final int RESULT = 105;
    private static final int RESULT_CANCELED = 0;
    private Bitmap avatarBmp;
    private String avatarUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        initviews();

    }

    @Override
    public int getLayoutId()
    {
        return R.layout.activity_me_2;
    }


    private void initviews()
    {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View contentview = inflater.inflate(R.layout.popup_process_name, null);
        sdvAvatar = (SimpleDraweeView) findViewById(R.id.sdv_edit_userInfo_avatar11);
        t1 = (TextView) contentview.findViewById(R.id.abandon_popup2);
        t2 = (TextView) contentview.findViewById(R.id.submit_popup2);
        t3 = (TextView) findViewById(R.id.textview11);
        r1 = (RelativeLayout) findViewById(R.id.r111);
        r2 = (RelativeLayout) findViewById(R.id.r2);
        e1 = (EditText) contentview.findViewById(R.id.name_popup_name2);


        avatarUrl = user.getString("imageUrl");

        if (!TextUtils.isEmpty(avatarUrl))
        {
            sdvAvatar.setImageURI(Uri.parse(avatarUrl));
        }

        r1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                chooseImgDialog();
            }
        });

//        sdvAvatar.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                chooseImgDialog();
//            }
//        });

        r1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                chooseImgDialog();
            }
        });

        r2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                contentview.setFocusable(true); // 这个很重要
                contentview.setFocusableInTouchMode(true);
                popupWindow = new PopupWindow(contentview, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);


                popupWindow.setFocusable(true);
                popupWindow.setAnimationStyle(R.style.anim_menu_bottombar);
                popupWindow.setOutsideTouchable(false);
                contentview.setOnKeyListener(new View.OnKeyListener()
                {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event)
                    {
                        if (keyCode == KeyEvent.KEYCODE_BACK)
                        {
                            popupWindow.dismiss();

                            return true;
                        }
                        return false;
                    }
                });
                popupWindow.showAtLocation(v, Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });


        t1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                popupWindow.dismiss();
            }
        });

        t2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                final String name = e1.getText().toString();
                user.put("nickname", name);
                user.saveInBackground(new SaveCallback()
                {
                    @Override
                    public void done(AVException e)
                    {
                        if (e == null)
                        {
                            t3.setText(name);
                            Log.i("lin", "---------------------->成功");
                        } else
                        {
                            Toast.makeText(MeActivity.this, "修改失败，请检查网络，或联系管理员", Toast.LENGTH_SHORT).show();
                            Log.i("lin", "---------------------->失败" + e);
                        }
                    }
                });
                popupWindow.dismiss();


            }
        });

        String nickname = user.getString("nickname");
        t3.setText(nickname);

    }

    private void chooseImgDialog()
    {
        new AlertDialog.Builder(MeActivity.this)
                .setTitle(getResources().getString(R.string.text_choose_image))
                .setCancelable(true)
                .setPositiveButton(getResources().getString(R.string.text_img_album), new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        fromGallery();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.text_img_camera), new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        fromCamera();
                    }
                }).show();
    }


    private void fromGallery()
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            startActivityForResult(intent, GALLERY_KITKAT_REQUEST);
        } else
        {
            startActivityForResult(intent, GALLERY_REQUEST);
        }
    }

    /**
     * 启动手机相机拍摄照片作为头像
     */
    private void fromCamera()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (FileUtil.hasSdcard())
        {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), IMAGE_NAME)));
            startActivityForResult(intent, CAMERA_REQUEST);
        } else
        {
            Log.i("sys", "--tc-->SD not exist");
        }
    }

    /**
     * 返回结果处理，这里需要注意resultCode，正常情况返回值为 -1 没有任何操作直接后退则返回 0
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // 用户进行了有效的操作，结果码不等于取消码的时候
        if (resultCode != RESULT_CANCELED)
        {
            switch (requestCode)
            {

                case GALLERY_REQUEST://从相册选择
                    cropPhoto(data.getData());
                    break;
                case GALLERY_KITKAT_REQUEST://从相册选择,兼容版本
                    cropPhoto(data.getData());
                    break;
                case CAMERA_REQUEST://从拍照选择
                    if (FileUtil.hasSdcard())
                    {
                        cropPhoto(Uri.fromFile(new File(Environment.getExternalStorageDirectory(), IMAGE_NAME)));
                    } else
                    {
                        Log.i("sys", "--tc-->EditUserInfo no sd card found");
                    }
                    break;
                case RESULT://选择完成，将头像放在ImageView中
                    if (data != null)
                    {
                        avatarBmp = data.getExtras().getParcelable("data");
                        sdvAvatar.setImageBitmap(ImageUtil.toRoundBitmap(avatarBmp));
                        updateUserInfo(avatarBmp);
                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 剪裁照片
     */
    public void cropPhoto(Uri uri)
    {
        if (uri == null)
        {
            Log.i("sys", "--tc-->The uri is not exist.");
            return;
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT)
        {
            String url = getPath(MeActivity.this, uri);
            if (url != null)
            {
                intent.setDataAndType(Uri.fromFile(new File(url)), "image/*");
            } else
            {
                Log.i("sys", "--tc-->EditUserInfo cropPhoto url is null");
            }
        } else
        {
            intent.setDataAndType(uri, "image/*");
        }

        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESULT);
    }


    //以下是关键，原本uri返回的是file:///...来着的，android4.4返回的是content:///...
    @SuppressLint("NewApi")
    public static String getPath(final Context context, final Uri uri)
    {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri))
        {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri))
            {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type))
                {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri))
            {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri))
            {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type))
                {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type))
                {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type))
                {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme()))
        {
            // Return the remote address
            if (isGooglePhotosUri(uri))
            {
                return uri.getLastPathSegment();
            }
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme()))
        {
            return uri.getPath();
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs)
    {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try
        {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst())
            {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally
        {
            if (cursor != null)
            {
                cursor.close();
            }
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri)
    {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri)
    {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri)
    {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri)
    {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    /**
     * 上传用户信息，首先上传头像，上传成功后赶回头像地址，然后上传其他信息
     */


    private void updateUserInfo(Bitmap avatar)
    {

        //如果头像为空，也就是用户没有上传头像，则使用之前的头像地址
        if (avatar == null)
        {
            if (avatarUrl == null)
            {
                uploadInfo("http://ac-torectpw.clouddn.com/zFEBEx5bXmVzJo2HHj1NhFKQn8cuX8rUent9xc8x.png");
            } else
            {
                uploadInfo(avatarUrl);
            }
        } else
        {
            final AVFile avatarFile = new AVFile("user_avatar.png", ImageUtil.bitmap2Bytes(avatar));
            avatarFile.saveInBackground(new SaveCallback()
            {
                @Override
                public void done(AVException e)
                {
                    if (e == null)
                    {
                        uploadInfo(avatarFile.getUrl());
                    }
                }
            });
        }
    }

    private void uploadInfo(String avatarUrl)
    {
        AVUser aaa = AVUser.getCurrentUser();

        aaa.setMobilePhoneNumber("13514680000");
        Log.i("lin", "------------------------------>" + avatarUrl);
        
        aaa.put("imageUrl", avatarUrl);

        aaa.signUpInBackground(new SignUpCallback()
        {
            public void done(AVException e)
            {
                if (e == null)
                {
                    Log.i("lin", "-------------------------------------->成功");
                    // successfully
                } else
                {
                    Log.i("lin", "-------------------------------------->失败" + e);
                    // failed
                }
            }
        });


    }

}















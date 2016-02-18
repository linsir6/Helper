package com.example.lin_sir.helper.Activity.fragments;

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
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;
import com.example.lin_sir.helper.Activity.activitys.ChangePasswordActivity;
import com.example.lin_sir.helper.Activity.activitys.FeedBackActivity;
import com.example.lin_sir.helper.Activity.activitys.MeActivity;
import com.example.lin_sir.helper.Activity.activitys.SignActivity;
import com.example.lin_sir.helper.Activity.utils.FileUtil;
import com.example.lin_sir.helper.Activity.utils.ImageUtil;
import com.example.lin_sir.helper.Activity.widget.CustomDialog;
import com.example.lin_sir.helper.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;

/**
 * Created by lin_sir on 2016/2/7.
 */
public class MeFragment extends Fragment implements View.OnClickListener
{
    //private GridView gridView;
    private TextView textView1, textView2, textView3, textView4;
    private static final String IMAGE_NAME = "user_avatar.jpg";
    private static final int GALLERY_REQUEST = 102;
    private static final int GALLERY_KITKAT_REQUEST = 103;
    private static final int CAMERA_REQUEST = 104;
    private static final int RESULT = 105;
    private static final int RESULT_CANCELED = 0;

    private Context context;
    private Bitmap avatarBmp;
    private SimpleDraweeView sdvAvatar;
    private LinearLayout l1, l2, l3, l4, l5;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Fresco.initialize(getActivity());
        View view = inflater.inflate(R.layout.fragment_me_2, null);
        sdvAvatar = (SimpleDraweeView) view.findViewById(R.id.sdv_edit_userInfo_avatar2);
        AVUser user = AVUser.getCurrentUser();
        String avatarUrl = user.getString("imageUrl");

        if (!TextUtils.isEmpty(avatarUrl))
        {
            sdvAvatar.setImageURI(Uri.parse(avatarUrl));
        }

        return view;


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        initviews();
        me();
    }

    private void initviews()
    {

        textView1 = (TextView) getView().findViewById(R.id.fr_me_name2);
        textView2 = (TextView) getView().findViewById(R.id.fr_me_phone2);
        l1 = (LinearLayout) getView().findViewById(R.id.li1);
        l2 = (LinearLayout) getView().findViewById(R.id.li2);
        l3 = (LinearLayout) getView().findViewById(R.id.li3);
        l4 = (LinearLayout) getView().findViewById(R.id.li4);
        l5 = (LinearLayout) getView().findViewById(R.id.ln5);
        l1.setOnClickListener(this);
        l2.setOnClickListener(this);
        l3.setOnClickListener(this);
        l4.setOnClickListener(this);
        l5.setOnClickListener(this);
        //textView3 = (TextView) view.findViewById(R.id.textview3);
        //textView4 = (TextView) view.findViewById(R.id.textview4);
        //gridView = (GridView) getView().findViewById(R.id.fr_me_grid);
        //gridView.setAdapter(new FragmentMeGridAdapter(getActivity()));

        //sdvAvatar.setOnClickListener(this);


    }


//    private void initevent()
//    {
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
//        {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
//            {
//                switch (position)
//                {
//                    case 0:
//                        Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
//                        startActivity(intent);
//                        break;
//                    case 1:
//                        Intent intent2 = new Intent(getActivity(), MeActivity.class);
//                        startActivity(intent2);
//                        break;
//                    case 2:
//                        Intent intent3 = new Intent(getActivity(), FeedBackActivity.class);
//                        startActivity(intent3);
//                        break;
//                    case 3:
//
//                        break;
//                    case 4:
//                        CustomDialog.Builder builder = new CustomDialog.Builder(getActivity());
//                        builder.setPositiveButton("退出当前帐号", new DialogInterface.OnClickListener()
//                        {
//                            public void onClick(DialogInterface dialog, int which)
//                            {
//                                dialog.dismiss();
//                                AVUser.logOut();
//                                AVUser currentUser = AVUser.getCurrentUser();
//                                Intent intent4 = new Intent(getActivity(), SignActivity.class);
//                                startActivity(intent4);
//                            }
//                        });
//
//                        builder.setNegativeButton("关闭Help",
//                                new android.content.DialogInterface.OnClickListener()
//                                {
//                                    public void onClick(DialogInterface dialog, int which)
//                                    {
//                                        getActivity().finish();
//                                    }
//                                });
//
//                        builder.create().show();
//
//                        break;
//
//
//                }
//            }
//        });
//    }

    private void me()
    {
        final AVUser user = AVUser.getCurrentUser();
        textView1.setText(user.getString("nickname"));
        textView2.setText(user.getString("username"));


        //textView4.setText(user.getString("Sex"));
        //textView3.setText(user.getString("Year"));
    }


    @Override
    public void onClick(View v)
    {

        switch (v.getId())
        {
            case R.id.sdv_edit_userInfo_avatar:
                chooseImgDialog();
                break;

            case R.id.li1:

                Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
                startActivity(intent);
                break;

            case R.id.li2:

                Toast.makeText(getActivity(), "管理员正在申请微信权限，稍后即将开启本功能", Toast.LENGTH_SHORT).show();
                break;

            case R.id.li3:

                Intent intent3 = new Intent(getActivity(), FeedBackActivity.class);
                startActivity(intent3);
                break;

            case R.id.li4:
                CustomDialog.Builder builder = new CustomDialog.Builder(getActivity());
                builder.setPositiveButton("退出当前帐号", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                        AVUser.logOut();
                        AVUser currentUser = AVUser.getCurrentUser();
                        Intent intent4 = new Intent(getActivity(), SignActivity.class);
                        startActivity(intent4);
                    }
                });

                builder.setNegativeButton("关闭Help",
                        new android.content.DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                getActivity().finish();
                            }
                        });

                builder.create().show();

                break;

            case R.id.ln5:
                Intent intent2 = new Intent(getActivity(), MeActivity.class);
                startActivity(intent2);
                break;
        }
    }

    private void chooseImgDialog()
    {
        new AlertDialog.Builder(getActivity())
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

    /**
     * 从本地相册选取图片作为头像
     */
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
            String url = getPath(getActivity(), uri);
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


}






















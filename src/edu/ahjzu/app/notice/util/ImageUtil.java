package edu.ahjzu.app.notice.util;

import java.io.FileDescriptor;
import java.io.FileInputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * ͼƬ������ ѹ��
 * 
 * @author zhao
 * 
 */
public class ImageUtil {
	/**
	 * get Bitmap
	 * 
	 * @param imgPath
	 * @param minSideLength
	 * @param maxNumOfPixels
	 * @return
	 */
	public static Bitmap getBitmap(String imgPath, int minSideLength,
			int maxNumOfPixels) {
		if (imgPath == null || imgPath.length() == 0)
			return null;

		try {
			FileDescriptor fd = new FileInputStream(imgPath).getFD();
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			// BitmapFactory.decodeFile(imgFile, options);
			BitmapFactory.decodeFileDescriptor(fd, null, options);

			options.inSampleSize = computeSampleSize(options, minSideLength,
					maxNumOfPixels);
			try {
				// ����һ��Ҫ�������û�false����Ϊ֮ǰ���ǽ������ó���true
				// ����inJustDecodeBoundsΪtrue��decodeFile��������ռ䣬����BitmapFactory���������BitmapΪNull,���ɼ����ԭʼͼƬ�ĳ��ȺͿ��
				options.inJustDecodeBounds = false;
				Bitmap bmp = BitmapFactory.decodeFile(imgPath, options);
				return bmp == null ? null : bmp;
			} catch (OutOfMemoryError err) {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * compute Sample Size
	 * 
	 * @param options
	 * @param minSideLength
	 * @param maxNumOfPixels
	 * @return
	 */
	private static int computeSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);

		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}

		return roundedSize;
	}

	/**
	 * compute Initial Sample Size
	 * 
	 * @param options
	 * @param minSideLength
	 * @param maxNumOfPixels
	 * @return
	 */
	private static int computeInitialSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;

		// �����޷�Χ
		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
				.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
				Math.floor(w / minSideLength), Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}

		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

	/**
	 * �õ�һ���߶ȵ�ͼƬ
	 * 
	 * @param height
	 * @param imagePath
	 * @return
	 */
	public Bitmap getImageByHeight(float height, String imagePath) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		// ��ȡ���ͼƬ�Ŀ�͸�
		Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options); // ��ʱ����bmΪ��
		options.inJustDecodeBounds = false;
		// �������ű�
		int be = (int) (options.outHeight / height);
		if (be <= 0)
			be = 1;
		options.inSampleSize = be;
		// ���¶���ͼƬ��ע�����Ҫ��options.inJustDecodeBounds ��Ϊ false
		bitmap = BitmapFactory.decodeFile(imagePath, options);
		return bitmap;

	}

	/**
	 * �õ�һ����ȵ�ͼƬ
	 * 
	 * @param width
	 * @param imagePath
	 * @return
	 */
	public Bitmap getImageByWidth(float width, String imagePath) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		// ��ȡ���ͼƬ�Ŀ�͸�
		Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options); // ��ʱ����bmΪ��
		options.inJustDecodeBounds = false;
		// �������ű�
		int be = (int) (options.outWidth / width);
		if (be <= 0)
			be = 1;
		options.inSampleSize = be;
		// ���¶���ͼƬ��ע�����Ҫ��options.inJustDecodeBounds ��Ϊ false
		bitmap = BitmapFactory.decodeFile(imagePath, options);
		return bitmap;

	}
	/**
	 * �õ�һ����Ȼ�߶ȵ�ͼƬ
	 * @param width ���
	 * @param height �߶�
	 * @param srcPath ͼƬ·��
	 * @return Bitmap
	 */
	public static Bitmap getBitmap(String srcPath,float width,float height) {  
        BitmapFactory.Options newOpts = new BitmapFactory.Options();  
        //��ʼ����ͼƬ����ʱ��options.inJustDecodeBounds ���true��  
        newOpts.inJustDecodeBounds = true;  
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);//��ʱ����bmΪ��  
          
        newOpts.inJustDecodeBounds = false;  
        int w = newOpts.outWidth;  
        int h = newOpts.outHeight;  
       
        //���űȡ������ǹ̶��������ţ�ֻ�ø߻��߿�����һ�����ݽ��м��㼴��  
        int be = 1;//be=1��ʾ������  
        if (w > h && w > width) {//�����ȴ�Ļ����ݿ�ȹ̶���С����  
            be = (int) (newOpts.outWidth / width);  
        } else if (w < h && h > height) {//����߶ȸߵĻ����ݸ߶ȹ̶���С����  
            be = (int) (newOpts.outHeight / height);  
        }  
        if (be <= 0)  
            be = 1;  
        newOpts.inSampleSize = be;//�������ű���  
        //���¶���ͼƬ��ע���ʱ�Ѿ���options.inJustDecodeBounds ���false��  
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);  
        return bitmap;
    }

	
}

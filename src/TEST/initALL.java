package TEST;

import android.app.Activity;

import android.util.DisplayMetrics;


public class initALL extends Activity{
	public initALL(){
		 DisplayMetrics metric = new DisplayMetrics();
	     getWindowManager().getDefaultDisplay().getMetrics(metric);
	     int width = metric.widthPixels;     // ��Ļ��ȣ����أ�
	     int height = metric.heightPixels;   // ��Ļ�߶ȣ����أ�
	     float density = metric.density;      // ��Ļ�ܶȣ�0.75 / 1.0 / 1.5��
         int densityDpi = metric.densityDpi;  // ��Ļ�ܶ�DPI��120 / 160 / 240��
}
}

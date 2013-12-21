package TEST;

import android.app.Activity;

import android.util.DisplayMetrics;


public class initALL extends Activity{
	public initALL(){
		 DisplayMetrics metric = new DisplayMetrics();
	     getWindowManager().getDefaultDisplay().getMetrics(metric);
	     int width = metric.widthPixels;     // ÆÁÄ»¿í¶È£¨ÏñËØ£©
	     int height = metric.heightPixels;   // ÆÁÄ»¸ß¶È£¨ÏñËØ£©
	     float density = metric.density;      // ÆÁÄ»ÃÜ¶È£¨0.75 / 1.0 / 1.5£©
         int densityDpi = metric.densityDpi;  // ÆÁÄ»ÃÜ¶ÈDPI£¨120 / 160 / 240£©
}
}

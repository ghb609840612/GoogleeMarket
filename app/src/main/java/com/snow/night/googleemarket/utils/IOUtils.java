package com.snow.night.googleemarket.utils;

import java.io.Closeable;
import java.io.IOException;

import android.util.Log;

public class IOUtils {
	/** 关闭流 */
	public static boolean close(Closeable io) {
		if (io != null) {
			try {
				io.close();
			} catch (IOException e) {
				Log.e("IOUtils", "", e);
			}
		}
		return true;
	}
}

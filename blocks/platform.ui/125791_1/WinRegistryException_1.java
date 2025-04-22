package org.eclipse.urischeme.internal.registration;


import java.io.UnsupportedEncodingException;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.prefs.Preferences;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

public class WinRegistry {
	private static final int KEY_READ = 1;
	private static final int KEY_SET = 2;
	private static final int KEY_DELETE = 0x10000;

	private static Method METHOD_stringToByteArray;
	private static Method METHOD_WinRegQueryValueEx;
	private static Method METHOD_toJavaValueString;
	private static Method METHOD_openKey;
	private static Method METHOD_closeKey;
	private static Method METHOD_WinRegSetValueEx1;
	private static Method METHOD_WinRegDeleteKey;



	static {
		try {
			Method method_setAccessible = AccessibleObject.class.getDeclaredMethod("setAccessible", boolean.class); //$NON-NLS-1$

			Class<?> prefClass = Preferences.userRoot().getClass();

			METHOD_stringToByteArray = prefClass.getDeclaredMethod("stringToByteArray", String.class); //$NON-NLS-1$
			method_setAccessible.invoke(METHOD_stringToByteArray, true);

			METHOD_toJavaValueString = prefClass.getDeclaredMethod("toJavaValueString", byte[].class); //$NON-NLS-1$
			method_setAccessible.invoke(METHOD_toJavaValueString, true);

			METHOD_openKey = prefClass.getDeclaredMethod("openKey", byte[].class, int.class, int.class); //$NON-NLS-1$
			method_setAccessible.invoke(METHOD_openKey, true);

			METHOD_closeKey = prefClass.getDeclaredMethod("closeKey", int.class); //$NON-NLS-1$
			method_setAccessible.invoke(METHOD_closeKey, true);

			METHOD_WinRegQueryValueEx = prefClass.getDeclaredMethod("WindowsRegQueryValueEx", int.class, byte[].class); //$NON-NLS-1$
			method_setAccessible.invoke(METHOD_WinRegQueryValueEx, true);


			METHOD_WinRegSetValueEx1 = prefClass.getDeclaredMethod("WindowsRegSetValueEx1", int.class, byte[].class, byte[].class); //$NON-NLS-1$
			method_setAccessible.invoke(METHOD_WinRegSetValueEx1, true);


			METHOD_WinRegDeleteKey = prefClass.getDeclaredMethod("WindowsRegDeleteKey", int.class, byte[].class); //$NON-NLS-1$
			method_setAccessible.invoke(METHOD_WinRegDeleteKey, true);

		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public static void setValueForKey(String key, String attribute, String value) throws WinRegistryException {
		final Preferences user = Preferences.userRoot();
		final Preferences systemRoot = Preferences.systemRoot();

		try {
			Integer handle = (Integer) METHOD_openKey.invoke(user, toByteArray(key), KEY_SET, KEY_SET);
			int result = (Integer) METHOD_WinRegSetValueEx1.invoke(null, handle, toByteArray(attribute), toByteArray(value));
			METHOD_closeKey.invoke(systemRoot, handle);
			if (result != 0) {
				throw new WinRegistryException("Unable to write to registry. Key = " + key + attribute + //$NON-NLS-1$
						", value: " + value); //$NON-NLS-1$
			}
		} catch (Exception e) {
			throw new WinRegistryException(e.getMessage(), e);
		}
	}

	public static String getValueForKey(String key, String attribute) throws WinRegistryException {
		final Preferences user = Preferences.userRoot();
		final Preferences systemRoot = Preferences.systemRoot();

		try {
			Integer handle = (Integer) METHOD_openKey.invoke(user, toByteArray(key), KEY_READ, KEY_READ);
			byte[] valb = (byte[]) METHOD_WinRegQueryValueEx.invoke(null, handle, toByteArray(attribute));
			String vals = (valb != null ? toString(valb) : null);
			METHOD_closeKey.invoke(systemRoot, handle);
			return vals;
		} catch (Exception e) {
			throw new WinRegistryException(e.getMessage(), e);
		}
	}

	public static void deleteKey(String key) throws WinRegistryException {
		final Preferences userRoot = Preferences.userRoot();
		IPath keyPath = new Path(key);

		try {
			String parent = keyPath.removeLastSegments(1).toOSString();
			String child = keyPath.lastSegment();

			Integer parentHandle = (Integer) METHOD_openKey.invoke(userRoot, toByteArray(parent), KEY_DELETE, KEY_DELETE);
			int result = (Integer) METHOD_WinRegDeleteKey.invoke(null, parentHandle, toByteArray(child));
			METHOD_closeKey.invoke(userRoot, parentHandle);
			if (result != 0) {
				throw new WinRegistryException("Unable to delete key = " + keyPath); //$NON-NLS-1$
			}
		} catch (Exception e) {
			throw new WinRegistryException(e.getMessage(), e);
		}
	}

	private static byte[] toByteArray(String str) {
		if (str == null) {
			return new byte[] { 0 };
		}
		try {
			return (byte[]) METHOD_stringToByteArray.invoke(null, str);
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	static String byteArrayToString(byte[] array) throws UnsupportedEncodingException {

		byte[] truncatedArray = new byte[array.length - 1];
		System.arraycopy(array, 0, truncatedArray, 0, truncatedArray.length);
		String result;
		try {
			result = new String(truncatedArray, "Windows-1252"); //$NON-NLS-1$
		} catch (UnsupportedEncodingException e) {
			result = new String(truncatedArray, "ISO-8859-1"); //$NON-NLS-1$
		}
		return result;
	}

	private static String toString(byte[] bytes) {
		try {
			return byteArrayToString(bytes);
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}









	HKLM, HKCU, HKCR, HKU, HKCC;

	public static RegistryRootKey getByString(String rootKey) {
		if (rootKey.equals("HKEY_LOCAL_MACHINE")) { //$NON-NLS-1$
			return HKLM;
		}
		if (rootKey.equals("HKEY_CURRENT_USER")) { //$NON-NLS-1$
			return HKCU;
		}
		if (rootKey.equals("HKEY_CLASSES_ROOT")) { //$NON-NLS-1$
			return HKCR;
		}
		if (rootKey.equals("HKEY_USERS")) { //$NON-NLS-1$
			return HKU;
		}
		if (rootKey.equals("HKEY_CURRENT_CONFIG")) { //$NON-NLS-1$
			return HKCC;
		}
		return null;
	}
}*/

}

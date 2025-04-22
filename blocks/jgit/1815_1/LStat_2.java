package org.eclipse.jgit.util.fs;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class FSAccess {

	public static final LStat lstat(String path) {
		try {
			File p = new File(path);
			Class<?> clazz = Class
					.forName("org.eclipse.jgit.util.fs.os.NativeFSAccess");
			assert (clazz != null);
			Method lstat = clazz.getDeclaredMethod("lstat"
			LStat stat = (LStat) (lstat.invoke(null
			System.out.println(stat.toString());
			return stat;
		} catch (ClassNotFoundException e) {
			System.out.println("Native jgit extension could not be loaded:");
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
}

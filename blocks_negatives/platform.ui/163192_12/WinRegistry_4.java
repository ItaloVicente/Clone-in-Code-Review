			IPath keyPath = new Path(key);
			String parent = keyPath.removeLastSegments(1).toOSString();
			String child = keyPath.lastSegment();
			Object parentHandle = METHOD_openKey.invoke(USER_ROOT, toByteArray(parent), KEY_DELETE, KEY_DELETE);
			int result = (Integer) METHOD_WinRegDeleteKey.invoke(null, parentHandle, toByteArray(child));
			METHOD_closeKey.invoke(USER_ROOT, parentHandle);
			if (result != 0) {

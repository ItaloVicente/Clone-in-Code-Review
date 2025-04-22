	private boolean isMacAppBundle(String location) {
		File bundleLoc = new File(location);
		File macosDir = new File(new File(bundleLoc, "Contents"), "MacOS"); //$NON-NLS-1$ //$NON-NLS-2$
		File plist = new File(new File(bundleLoc, "Contents"), "Info.plist"); //$NON-NLS-1$ //$NON-NLS-2$
		return bundleLoc.isDirectory() && macosDir.isDirectory() && plist.isFile();
	}


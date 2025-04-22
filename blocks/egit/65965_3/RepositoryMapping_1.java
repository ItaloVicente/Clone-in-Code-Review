		final IPath gLoc = Path.fromOSString(gitDir.getPath())
				.removeTrailingSeparator();
		if (!gitDir.isAbsolute()) {
			gitDirPathString = gLoc.toPortableString();
			return;
		}
		final IPath cLoc = location.removeTrailingSeparator();
		final IPath gLocParent = gLoc.removeLastSegments(1);

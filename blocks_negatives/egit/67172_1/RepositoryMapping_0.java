		final IPath cLoc = location.removeTrailingSeparator();
		final IPath gLocParent = gLoc.removeLastSegments(1);

		if (cLoc.isPrefixOf(gLoc)) {
			int matchingSegments = gLoc.matchingFirstSegments(cLoc);
			IPath remainder = gLoc.removeFirstSegments(matchingSegments);
			String device = remainder.getDevice();
			if (device == null)
				gitDirPathString = remainder.toPortableString();
			else
				gitDirPathString = remainder.toPortableString().substring(
						device.length());
		} else if (gLocParent.isPrefixOf(cLoc)) {
			int cnt = cLoc.segmentCount() - cLoc.matchingFirstSegments(gLocParent);
			while (cnt-- > 0) {
			}
			p.append(gLoc.segment(gLoc.segmentCount() - 1));
			gitDirPathString = p.toString();
		} else {
			gitDirPathString = gLoc.toPortableString();

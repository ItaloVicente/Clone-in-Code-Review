			String gitDirParentCandidate = ti.getText(1);
			IPath thisPath = Path.fromOSString(gitDirParentCandidate);
			if (p == null)
				p = thisPath;
			else {
				int n = p.matchingFirstSegments(thisPath);
				p = p.removeLastSegments(p.segmentCount() - n);

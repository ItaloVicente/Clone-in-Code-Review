	private static IPath proposeNewRepositoryPath(TreeItem[] treeItems) {
		IPath p = null;
		for (TreeItem ti : treeItems) {
			String gitDirParentCandidate = ti.getText(1);
			if (gitDirParentCandidate.equals("")) //$NON-NLS-1$
				continue;
			if (ti.getItemCount() > 0)
				if (hasRepositoryInOwnDirectory(ti.getItems()))
					return null;
			if (hasRepositoryInOwnDirectory(ti))
				return null;
			IPath thisPath = Path.fromOSString(gitDirParentCandidate);
			if (p == null)
				p = thisPath;
			else {
				int n = p.matchingFirstSegments(thisPath);
				p = p.removeLastSegments(p.segmentCount() - n);
			}
		}
		return p;
	}

	private static boolean hasRepositoryInOwnDirectory(TreeItem... items) {
		for (TreeItem item : items)
			if (".git".equals(item.getText(2))) //$NON-NLS-1$
				return true;
		return false;
	}


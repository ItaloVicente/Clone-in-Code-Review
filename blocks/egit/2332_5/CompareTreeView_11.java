			if (element instanceof String)
				return (String) element;
			IPath path = ((PathNode) element).path;
			if (path.segmentCount() == 0)
				return UIText.CompareTreeView_RepositoryRootName;
			return path.lastSegment();


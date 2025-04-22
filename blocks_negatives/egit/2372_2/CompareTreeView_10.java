			if (element instanceof IContainer) {
				IPath containerPath = new Path(repositoryMapping
						.getRepoRelativePath((IContainer) element));
				for (IPath rightOnlyPath : rightOnly) {
					if (rightOnlyPath.removeLastSegments(1).equals(
							containerPath)) {
						childList.add(rightVersionMap.get(rightOnlyPath));
						rebuildArray = true;
					}

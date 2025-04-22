			minumumPath = null;
			IPath p = null;
			for (TreeItem ti : tree.getSelection()) {
				if (ti.getItemCount() > 0)
					continue;
				String path = ti.getText(2);
					p = null;
					break;
				}
				String gitDirParentCandidate = ti.getText(1);
				IPath thisPath = Path.fromOSString(gitDirParentCandidate);
				if (p == null)
					p = thisPath;
				else {
					int n = p.matchingFirstSegments(thisPath);
					p = p.removeLastSegments(p.segmentCount() - n);
				}
			}

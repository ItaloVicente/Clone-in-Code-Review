
				boolean isPageCompleted = true;
				for (Object checked : treeViewer.getCheckedElements()) {
					SyncData data = repoMapping.get(checked);
					if (data.srcRev == null || data.dstRev == null) {
						isPageCompleted = false;
						break;
					}
				}
				setPageComplete(isPageCompleted);

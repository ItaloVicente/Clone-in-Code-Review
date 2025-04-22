		} else if (inFiles != null) {
			IPath workdirPath = new Path(db.getWorkTree().getPath());
			IPath gitDirPath = new Path(db.getDirectory().getPath());
			int segmentCount = workdirPath.segmentCount();
			paths = new ArrayList<String>(inFiles.length);
			for (File file : inFiles) {
				IPath filePath;
				if (showAllFilter == ShowFilter.SHOWALLFOLDER) {
					filePath = new Path(file.getParentFile().getPath());
				} else if (showAllFilter == ShowFilter.SHOWALLPROJECT
						|| showAllFilter == ShowFilter.SHOWALLREPO) {
					continue;
				} else /* if (showAllFilter == ShowFilter.SHOWALLRESOURCE) */{
					filePath = new Path(file.getPath());
				}

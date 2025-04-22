	GitSynchronizeDataSet getSyncData() throws IOException {
		GitSynchronizeDataSet result = new GitSynchronizeDataSet();
		for (Object checked : treeViewer.getCheckedElements()) {
			SyncData data = repoMapping.get(checked);
			if (data.srcRev != null && data.dstRev != null)
				result.add(new GitSynchronizeData((Repository) checked,
						data.srcRev, data.dstRev, data.includeLocal));

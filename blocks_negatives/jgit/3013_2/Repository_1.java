		File mergeHeadFile = new File(gitDir, Constants.MERGE_HEAD);
		if (heads != null) {
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(mergeHeadFile));
			try {
				for (ObjectId id : heads) {
					id.copyTo(bos);
					bos.write('\n');
				}
			} finally {
				bos.close();
			}
		} else {
			FileUtils.delete(mergeHeadFile);
		}

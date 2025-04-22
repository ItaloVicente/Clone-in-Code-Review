
	public void writeMergeHeads(List<ObjectId> heads) throws IOException {
		File mergeHeadFile = new File(gitDir
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
			mergeHeadFile.delete();
		}
	}

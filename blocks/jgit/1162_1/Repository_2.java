
	public void writeMergeHeads(List<ObjectId> heads) throws IOException {
		File mergeHeadFile = new File(gitDir
		if (heads != null) {
			FileOutputStream fos = new FileOutputStream(mergeHeadFile);
			try {
				boolean first = true;
				for (ObjectId id : heads) {
					if (first)
						first = false;
					else
						fos.write(10);
					fos.write(id.name().getBytes(Constants.CHARACTER_ENCODING));
				}
			} finally {
				fos.close();
			}
		} else {
			mergeHeadFile.delete();
		}
	}

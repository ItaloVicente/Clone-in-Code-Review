	private void writeSizeIdx(int minSize) throws IOException {
		try (FileOutputStream os = new FileOutputStream(tmpObjSizeIdx)) {
			PackObjectSizeIndexWriter iw = PackObjectSizeIndexWriter
					.createWriter(os
			iw.write(getSortedObjectList(null));
			os.getChannel().force(true);
		}
	}


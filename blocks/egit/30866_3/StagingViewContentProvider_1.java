	void setFileNameMode(boolean enable) {
		comparator.fileNameMode = enable;
		if (content != null) {
			Arrays.sort(content, comparator);
		}
	}


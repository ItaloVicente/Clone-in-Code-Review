		boolean goodFile = false;
		try {
			checkFile(idx
			goodFile = true;
			return new OpenFile(fd);
		} finally {
			if (!goodFile) {
				invalid = true;
				try {
					fd.close();
				} catch (IOException err) {
				}
			}
		}

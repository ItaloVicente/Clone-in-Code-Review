		File path = new File(getCurrentDirectory()
		FileInputStream in = new FileInputStream(path);
		try {
			long sz = in.getChannel().size();
			f.setId(inserter.insert(Constants.OBJ_BLOB
			inserter.flush();
		} finally {
			inserter.release();
			in.close();
		}

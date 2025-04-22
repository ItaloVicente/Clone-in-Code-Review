		ObjectId id;
		FileInputStream in = new FileInputStream(f);
		try {
			id = inserter.insert(Constants.OBJ_BLOB, in.getChannel().size(), in);
		} finally {
			in.close();
		}

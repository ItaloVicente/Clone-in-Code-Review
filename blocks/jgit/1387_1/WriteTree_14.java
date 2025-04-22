		try {
			t.setId(inserter.insert(Constants.OBJ_TREE
			inserter.flush();
		} finally {
			inserter.release();
		}

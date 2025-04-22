			ObjectInserter inserter = db.newObjectInserter();
			try {
				InputStream in = new FileInputStream(f);
				try {
					sha1 = inserter.insert(Constants.OBJ_BLOB
				} finally {
					in.close();
				}
				inserter.flush();
			} finally {
				inserter.release();
			}

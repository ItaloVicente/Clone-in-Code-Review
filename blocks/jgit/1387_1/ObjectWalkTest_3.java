			final ObjectInserter inserter = db.newObjectInserter();
			try {
				A_A.setId(inserter.insert(Constants.OBJ_TREE
				A_B.setId(inserter.insert(Constants.OBJ_TREE
				A.setId(inserter.insert(Constants.OBJ_TREE
				root.setId(inserter.insert(Constants.OBJ_TREE
				inserter.flush();
			} finally {
				inserter.release();
			}

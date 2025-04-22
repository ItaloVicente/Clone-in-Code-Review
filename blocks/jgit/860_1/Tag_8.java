			ObjectInserter odi = objdb.newObjectInserter();
			try {
				id = odi.insert(Constants.OBJ_TAG
				odi.flush();
				setTagId(id);
			} finally {
				odi.release();
			}

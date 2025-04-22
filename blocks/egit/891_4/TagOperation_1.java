			ObjectInserter ins = repo.newObjectInserter();
			try {
				tag.setTagId(ins.insert(Constants.OBJ_TAG, ins.format(tag)));
				ins.flush();
			} finally {
				ins.release();
			}

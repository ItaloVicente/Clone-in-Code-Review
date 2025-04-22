
				ObjectInserter inserter = tree.getRepository().newObjectInserter();
				try {
					tree.setId(inserter.insert(Constants.OBJ_TREE, tree.format()));
					inserter.flush();
				} finally {
					inserter.release();
				}

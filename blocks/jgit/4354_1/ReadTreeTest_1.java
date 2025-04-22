				writeTrashFile(e.getKey()
				ObjectInserter inserter = db.newObjectInserter();
				final ObjectId id = inserter.insert(Constants.OBJ_BLOB
						.getValue().getBytes(Constants.CHARSET));
				editor.add(new DirCacheEditor.DeletePath(e.getKey()));
				editor.add(new DirCacheEditor.PathEdit(e.getKey()) {
					@Override
					public void apply(DirCacheEntry ent) {
						ent.setFileMode(FileMode.REGULAR_FILE);
						ent.setObjectId(id);
						ent.setUpdateNeeded(false);
					}
				});

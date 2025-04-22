				ByteArrayInputStream in = new ByteArrayInputStream(
						content.array(), 0, content.limit());
				ent.setObjectId(
						inserter.insert(Constants.OBJ_BLOB, content.limit(),
								in));
				inserter.flush();

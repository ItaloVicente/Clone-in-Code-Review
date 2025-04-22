				Attributes attr = LfsFactory.getAttributesForPath(repo,
						ent.getPathString());
				try (LfsInputStream lfs = LfsFactory.getInstance()
						.applyCleanFilter(repo,
						new ByteArrayInputStream(content), contentLength,
								attr.get(Constants.ATTR_MERGE))) {
					ent.setLength(lfs.getLength());
					ent.setObjectId(inserter.insert(Constants.OBJ_BLOB,
							lfs.getLength(), lfs));
					inserter.flush();
				}

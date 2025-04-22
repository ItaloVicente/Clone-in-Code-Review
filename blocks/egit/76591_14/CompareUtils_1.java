				Attributes attr = LfsHelper.getAttributesForPath(repo,
						ent.getPathString());
				LfsInputStream lfs = LfsHelper.getCleanFiltered(repo,
						new ByteArrayInputStream(content), contentLength,
						attr.get(Constants.ATTR_MERGE));
				ent.setLength(lfs.length);
				try (InputStream is = lfs.stream) {
					ent.setObjectId(inserter.insert(Constants.OBJ_BLOB,
							lfs.length, is));
					inserter.flush();
				}

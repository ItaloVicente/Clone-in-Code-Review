			try (ByteArrayInputStream bis = new ByteArrayInputStream(content)) {
				Attributes attr = LfsHelper.getAttributesForPath(repo,
						ent.getPathString());
				ent.setObjectId(inserter.insert(Constants.OBJ_BLOB,
						contentLength, LfsHelper.getCleanFiltered(repo, bis,
								attr.get(Constants.ATTR_MERGE))));

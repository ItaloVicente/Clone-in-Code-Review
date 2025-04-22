				FileMode mode = f.getIndexFileMode(c);
				entry.setFileMode(mode);

				if (GITLINK != mode) {
					entry.setLength(f.getEntryLength());
					entry.setLastModified(f.getEntryLastModified());
					long len = f.getEntryContentLength();
					try (InputStream in = f.openEntryStream()) {
						ObjectId id = inserter.insert(OBJ_BLOB
						entry.setObjectId(id);

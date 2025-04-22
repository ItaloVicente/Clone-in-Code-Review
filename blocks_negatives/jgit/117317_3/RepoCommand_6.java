						objectId = inserter.insert(Constants.OBJ_BLOB,
								link.getBytes(
										Constants.CHARACTER_ENCODING));
						dcEntry = new DirCacheEntry(linkfile.dest);
						dcEntry.setObjectId(objectId);
						dcEntry.setFileMode(FileMode.SYMLINK);
						builder.add(dcEntry);
					}
				}
				String content = cfg.toText();

				final DirCacheEntry dcEntry = new DirCacheEntry(Constants.DOT_GIT_MODULES);
				ObjectId objectId = inserter.insert(Constants.OBJ_BLOB,
						content.getBytes(Constants.CHARACTER_ENCODING));
				dcEntry.setObjectId(objectId);
				dcEntry.setFileMode(FileMode.REGULAR_FILE);
				builder.add(dcEntry);

				if (recordSubmoduleLabels) {
					final DirCacheEntry dcEntryAttr = new DirCacheEntry(Constants.DOT_GIT_ATTRIBUTES);
					ObjectId attrId = inserter.insert(Constants.OBJ_BLOB,
							attributes.toString().getBytes(Constants.CHARACTER_ENCODING));
					dcEntryAttr.setObjectId(attrId);
					dcEntryAttr.setFileMode(FileMode.REGULAR_FILE);
					builder.add(dcEntryAttr);
				}

				ObjectId head = db.resolve(Constants.HEAD);
				if (head == null) {
					throw die(MessageFormat.format(CLIText.get().noSuchRef,
							Constants.HEAD));
				}
				generator.push(null, head);
				if (!db.isBare()) {
					DirCache dc = db.readDirCache();
					int entry = dc.findEntry(file);
					if (0 <= entry) {
						generator.push(null, dc.getEntry(entry).getObjectId());
					} else {
						throw die(MessageFormat.format(
								CLIText.get().noSuchPathInRef, file,
								Constants.HEAD));
					}

					File inTree = new File(db.getWorkTree(), file);
					if (db.getFS().isFile(inTree)) {
						generator.push(null, new RawText(inTree));
					}
				}

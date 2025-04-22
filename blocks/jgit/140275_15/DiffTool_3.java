			private List<DiffEntry> getFiles()
					throws RevisionSyntaxException
					IncorrectObjectTypeException
				diffFmt.setRepository(db);
				if (cached) {
					if (oldTree == null) {
						if (head == null) {
							die(MessageFormat.format(CLIText.get().notATree
									HEAD));
						}
						CanonicalTreeParser p = new CanonicalTreeParser();
						try (ObjectReader reader = db.newObjectReader()) {
							p.reset(reader
						}
						oldTree = p;
					}
					newTree = new DirCacheIterator(db.readDirCache());
				} else if (oldTree == null) {
					oldTree = new DirCacheIterator(db.readDirCache());
					newTree = new FileTreeIterator(db);
				} else if (newTree == null) {
					newTree = new FileTreeIterator(db);
				}

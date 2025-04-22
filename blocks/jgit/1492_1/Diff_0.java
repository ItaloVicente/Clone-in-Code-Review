			if (cached) {
				if (oldTree == null) {
					ObjectId head = db.resolve(HEAD + "^{tree}");
					if (head == null)
						die(MessageFormat.format(CLIText.get().notATree
					CanonicalTreeParser p = new CanonicalTreeParser();
					ObjectReader reader = db.newObjectReader();
					try {
						p.reset(reader
					} finally {
						reader.release();
					}
					oldTree = p;
				}
				newTree = new DirCacheIterator(db.readDirCache());
			} else if (oldTree == null) {
				oldTree = new DirCacheIterator(db.readDirCache());
				newTree = new FileTreeIterator(db);
			} else if (newTree == null)
				newTree = new FileTreeIterator(db);


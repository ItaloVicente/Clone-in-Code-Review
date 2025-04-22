			} else {
				if (oldTree == null)
					oldTree = new DirCacheIterator(repo.readDirCache());
				if (newTree == null)
					newTree = new FileTreeIterator(repo);
			}

			if (fileTreeIterator == null || !repo.equals(lastRepo)) {
				lastRepo = repo;
				fileTreeIterator = new FileTreeIterator(repo);
			} else
				fileTreeIterator.reset();

			tw.addTree(fileTreeIterator);

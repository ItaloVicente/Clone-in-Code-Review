
				if (headCommit == null)
					return null;
				RevCommit previousCommit = rw.next();
				if (previousCommit == null)
					return null;

				if (previousPath.get() == null)
					previousPath.set(getRepositoryPath());
				return new PreviousCommit(previousCommit, previousPath.get());

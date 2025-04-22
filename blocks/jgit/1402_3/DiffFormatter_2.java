			if (db == null)
				throw new IllegalStateException(
						JGitText.get().repositoryIsRequired);
			ObjectReader reader = db.newObjectReader();
			byte[] aRaw
			try {
				aRaw = open(reader
				bRaw = open(reader
			} finally {
				reader.release();
			}

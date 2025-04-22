			String toAdd = getEntry(gitignore.getLocation().toFile(), entry);
			ByteArrayInputStream entryBytes = asStream(toAdd);
			if (gitignore.exists()) {
				gitignore.appendContents(entryBytes, true, true,
						progress.newChild(1));
			} else {
				gitignore.create(entryBytes, true, progress.newChild(1));
			}

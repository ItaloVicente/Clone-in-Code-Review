			entry = getEntry(gitignore.getLocation().toFile(), entry);
			ByteArrayInputStream entryBytes = asStream(entry);
			if (gitignore.exists())
				gitignore.appendContents(entryBytes, true, true, monitor);
			else
				gitignore.create(entryBytes, true, monitor);

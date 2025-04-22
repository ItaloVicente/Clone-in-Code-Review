		}

		refs = new RefDatabase(this);
		if (objectDir != null)
			objectDatabase = new ObjectDirectory(FS.resolve(objectDir
					alternateObjectDir);
		else
			objectDatabase = new ObjectDirectory(FS.resolve(gitDir
					alternateObjectDir);

		if (indexFile != null)
			this.indexFile = indexFile;
		else
			this.indexFile = new File(gitDir

		if (objectDatabase.exists()) {

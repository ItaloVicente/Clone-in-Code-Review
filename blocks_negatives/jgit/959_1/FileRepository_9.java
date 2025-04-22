		if (objectDir != null) {
			objectDatabase = new ObjectDirectory(repoConfig, //
					fs.resolve(objectDir, ""), //
					alternateObjectDir, //
					fs);
		} else {
			objectDatabase = new ObjectDirectory(repoConfig, //
					fs.resolve(gitDir, "objects"), //
					alternateObjectDir, //
					fs);
		}

		if (indexFile != null)
			this.indexFile = indexFile;
		else
			this.indexFile = new File(gitDir, "index");

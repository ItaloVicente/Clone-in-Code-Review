		if (objectDir != null)
			objectDatabase = new ObjectDirectory(fs.resolve(objectDir, ""),
					alternateObjectDir, fs);
		else
			objectDatabase = new ObjectDirectory(fs.resolve(gitDir, "objects"),
					alternateObjectDir, fs);

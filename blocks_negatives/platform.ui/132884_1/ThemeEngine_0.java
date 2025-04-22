		File oldModDir= new File(
		if (oldModDir.exists()) {
			File done = new File(oldModDir, ".processed");
			if (!done.exists()) {
				try {
					done.createNewFile();
					File[] oldModifiedFiles = oldModDir.listFiles();
					if (oldModifiedFiles == null) {
						throw new IOException(
					}
					for (File oldModifiedFile : oldModifiedFiles) {
						if (oldModifiedFile.getName().contains(".css")) {
							copyFile(oldModifiedFile.getPath(), path
									+ File.separator
									+ oldModifiedFile.getName());
						}
					}
				} catch (IOException e1) {
					/* ignore */
				}
			}
		}



				DirCacheEntry entry = locateEntry(cache);
				if (entry != null) {
					blobId = entry.getObjectId();
					if (FileMode.GITLINK.equals(entry.getFileMode())) {
						isGitlink = true;
					} else {
						if (blobId != null) {
							metadata = getMetadata(cache);
						}
					}
				} else if (!db.isBare()) {
					File onDisk = new File(db.getWorkTree(), path);
					isGitlink = onDisk.isDirectory();

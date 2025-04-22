			if (lastModified == 0) {
				try {
					lastModified = fs.lastModified(file);
				} catch (IOException e) {
					lastModified = 0;
				}
			}

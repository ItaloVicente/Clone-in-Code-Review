		Enumeration entries = zipFile.entries();
		while (entries.hasMoreElements()) {
			ZipEntry entry = (ZipEntry) entries.nextElement();
			monitor.subTask(entry.getName());
			File aFile = computeLocation(entry.getName()).toFile();
			File parentFile = null;
			try {
				if (entry.isDirectory()) {
					aFile.mkdirs();
				} else {
					parentFile = aFile.getParentFile();
					if (!parentFile.exists())
						parentFile.mkdirs();
					if (!aFile.exists())
						aFile.createNewFile();
					copy(zipFile.getInputStream(entry), new FileOutputStream(aFile));
					if (entry.getTime() > 0)
						aFile.setLastModified(entry.getTime());

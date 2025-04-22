		while (entries.hasMoreElements()) {
			try {
				ZipEntry entry = entries.nextElement();
				File destinationfile = new File(zipDestinationDir, entry.getName());
				String canonicalDestinationFile = destinationfile.getCanonicalPath();

				if (!canonicalDestinationFile.startsWith(canonicalDestinationDirPath + File.separator)) {
					invalidEntries.add(entry.getName());
					throw new IOException("Entry is outside of the target dir: " + entry.getName()); //$NON-NLS-1$
				} else if (!entry.isDirectory()) {
					IPath path = new Path(entry.getName()).addTrailingSeparator();
					int pathSegmentCount = path.segmentCount();

					for (int i = 1; i < pathSegmentCount; i++) {
						createContainer(path.uptoSegment(i));
					}
					createFile(entry);

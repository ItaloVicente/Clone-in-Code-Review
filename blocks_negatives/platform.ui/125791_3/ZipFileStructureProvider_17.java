			try {
				ZipEntry entry = entries.nextElement();
				File destinationfile = new File(zipDestinationDir, entry.getName());
				String canonicalDestinationFile = destinationfile.getCanonicalPath();

				if (!canonicalDestinationFile.startsWith(canonicalDestinationDirPath + File.separator)) {
					invalidEntries.add(entry.getName());
				} else if (!entry.isDirectory()) {
					IPath path = new Path(entry.getName()).addTrailingSeparator();
					int pathSegmentCount = path.segmentCount();

					for (int i = 1; i < pathSegmentCount; i++) {
						createContainer(path.uptoSegment(i));
					}
					createFile(entry);

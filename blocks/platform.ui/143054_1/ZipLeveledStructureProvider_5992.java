			try {
				ZipEntry entry = entries.nextElement();
				File destinationfile = new File(zipDestinationDir, entry.getName());
				String canonicalDestinationFile = destinationfile.getCanonicalPath();

				if (!canonicalDestinationFile.startsWith(canonicalDestinationDirPath + File.separator)) {
					invalidEntries.add(entry.getName());
					throw new IOException("Entry is outside of the target dir: " + entry.getName()); //$NON-NLS-1$
				}
				IPath path = new Path(entry.getName()).addTrailingSeparator();

				if (entry.isDirectory()) {
					createContainer(path);
				} else {
					int pathSegmentCount = path.segmentCount();
					if (pathSegmentCount > 1) {
						createContainer(path.uptoSegment(pathSegmentCount - 1));
					}
					createFile(entry);

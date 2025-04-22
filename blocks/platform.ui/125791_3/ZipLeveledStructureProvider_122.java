			ZipEntry entry = entries.nextElement();
			IPath path = new Path(entry.getName()).addTrailingSeparator();

			if (entry.isDirectory()) {
				createContainer(path);
			} else
			{
				int pathSegmentCount = path.segmentCount();
				if (pathSegmentCount > 1) {
					createContainer(path.uptoSegment(pathSegmentCount - 1));

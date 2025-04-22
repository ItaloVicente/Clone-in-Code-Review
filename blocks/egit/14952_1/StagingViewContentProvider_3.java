		List<Object> compressedRootsList = new ArrayList<Object>();

		for (StagingEntry file : content) {
			IPath path = new Path(file.getPath());
			if (path.segmentCount() == 1) {
				compressedRootsList.add(file);
			} else {
				IPath folderPath = path.removeLastSegments(1);
				folderPaths.add(folderPath);
				IPath p = folderPath;
				while (p.segmentCount() != 0) {
					IPath parent = p.removeLastSegments(1);
					String childSegment = p.lastSegment();
					String existingChildSegment = childSegments.get(parent);
					if (existingChildSegment == null) {
						childSegments.put(parent, childSegment);
					} else if (!childSegment.equals(existingChildSegment)) {
						folderPaths.add(parent);
					}
					p = p.removeLastSegments(1);

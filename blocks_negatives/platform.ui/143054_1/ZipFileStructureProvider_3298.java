        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            if (!entry.isDirectory()) {
                IPath path = new Path(entry.getName()).addTrailingSeparator();
                int pathSegmentCount = path.segmentCount();

                for (int i = 1; i < pathSegmentCount; i++) {
					createContainer(path.uptoSegment(i));

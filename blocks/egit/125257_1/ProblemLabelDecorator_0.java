			if (resource == null) {
				continue;
			}
			RepositoryMapping mapping = RepositoryMapping.getMapping(resource);
			if (mapping == null || mapping.getRepository().isBare()) {
				continue;
			}
			IPath path = resource.getLocation();
			if (path == null) {
				continue;
			}
			File workTree = mapping.getWorkTree();
			if (workTree == null) {
				continue;
			}
			int n = new Path(workTree.getAbsolutePath()).segmentCount();
			for (int i = path.segmentCount(); i > n; i--) {
				paths.add(path);
				path = path.removeLastSegments(1);

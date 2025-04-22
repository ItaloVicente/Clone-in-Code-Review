	private GitModelObject addChild(Change change, String nestedPath) {
		GitModelObject firstObject = null;
		IPath tmpLocation = getLocation();
		String[] segments = nestedPath.split("/"); //$NON-NLS-1$
		GitModelObjectContainer tmpPartent = this;
		Map<String, GitModelObject> tmpCache = cachedTreeMap;

		for (int i = 0; i < segments.length; i++) {
			String segment = segments[i];
			tmpLocation = tmpLocation.append(segment);
			if (i < segments.length - 1) {
				GitModelTree tree = (GitModelTree) tmpCache.get(segment);
				if (tree == null) {
					tree = new GitModelTree(tmpPartent, tmpLocation, change.getKind());
					tmpCache.put(segment, tree);
				}
				tmpPartent = tree;
				tmpCache = tree.cachedTreeMap;
				if (i == 0)
					firstObject = tmpPartent;
			} else { // handle last segment, it should be a file name
				GitModelBlob blob = new GitModelBlob(tmpPartent, repo, change,
						tmpLocation);
				tmpCache.put(segment, blob);
				if (i == 0)
					firstObject = blob;

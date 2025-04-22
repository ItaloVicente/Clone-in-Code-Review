		Path ppath = new Path(path);
		String projectName = ppath.lastSegment();
		URI locationURI;
		if (!ppath.lastSegment().equals(path)) {
			locationURI = URIUtil.toURI(URIUtil.toPath(root.getRawLocationURI()).append(path));
		} else
			locationURI = null;

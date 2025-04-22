		Path ppath = new Path(path);
		String projectName = ppath.lastSegment();
		URI locationURI;
		URI top;
		if (insidews) {
			top = root.getRawLocationURI();
		} else {
			File tempName;
			try {
				tempName = testUtils.createTempDir("wssupplement");
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			top = URIUtil.toURI(tempName.getAbsolutePath());
		}
		if (!ppath.lastSegment().equals(path)) {
			locationURI = URIUtil.toURI(URIUtil.toPath(top).append(path));
		} else
			locationURI = null;

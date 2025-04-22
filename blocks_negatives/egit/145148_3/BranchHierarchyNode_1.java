	/**
	 * @return the child paths
	 * @throws IOException
	 */
	public List<IPath> getChildPaths() throws IOException {
		List<IPath> result = new ArrayList<>();
		for (IPath myPath : getPathList()) {
			if (getObject().isPrefixOf(myPath)) {
				int segmentDiff = myPath.segmentCount()
						- getObject().segmentCount();
				if (segmentDiff > 1) {
					IPath newPath = getObject().append(
							myPath.segment(getObject().segmentCount()));
					if (!result.contains(newPath))
						result.add(newPath);
				}
			}
		}
		return result;
	}

	/**
	 * @return the direct child Refs (branches) only
	 * @throws IOException
	 */
	public List<Ref> getChildRefs() throws IOException {
		List<Ref> childRefs = new ArrayList<>();
		for (IPath myPath : getPathList()) {
			if (getObject().isPrefixOf(myPath)) {
				int segmentDiff = myPath.segmentCount()
						- getObject().segmentCount();
				if (segmentDiff == 1) {
					Ref ref = getRepository()
							.findRef(myPath.toPortableString());
					childRefs.add(ref);
				}
			}
		}
		return childRefs;
	}


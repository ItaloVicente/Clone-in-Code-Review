	public List<Ref> getChildRefsRecursive() throws IOException {
		List<Ref> childRefs = new ArrayList<>();
		for (IPath myPath : getPathList()) {
			if (getObject().isPrefixOf(myPath)) {
				Ref ref = getRepository().getRef(myPath.toPortableString());
				childRefs.add(ref);
			}
		}
		return childRefs;
	}


		List<Ref> childRefs = new ArrayList<>();
		for (IPath myPath : getPathList()) {
			if (getObject().isPrefixOf(myPath)) {
				Ref ref = getRepository().exactRef(myPath.toPortableString());
				childRefs.add(ref);
			}
		}
		return childRefs;

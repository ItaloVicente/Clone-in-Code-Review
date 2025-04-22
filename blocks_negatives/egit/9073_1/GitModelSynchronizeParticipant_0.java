			child.putString(CONTAINER_PATH_KEY, getPathForContainer(mapping.getContainer()));
			child.putString(SRC_REV_KEY, gsd.getSrcRev());
			child.putString(DST_REV_KEY, gsd.getDstRev());
			child.putBoolean(INCLUDE_LOCAL_KEY, gsd.shouldIncludeLocal());
			Set<IContainer> includedPaths = gsd.getIncludedPaths();
			if (includedPaths != null && !includedPaths.isEmpty()) {
				IMemento paths = child.createChild(INCLUDED_PATHS_NODE_KEY);
				for (IContainer container : includedPaths) {
					String path = getPathForContainer(container);
					paths.createChild(INCLUDED_PATH_KEY).putString(
							INCLUDED_PATH_KEY, path);

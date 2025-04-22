
		if (inCore) {
			implicitDirCache = false;
			dircache = DirCache.newInCore();
		} else {
			implicitDirCache = true;
			workingTreeOptions = local.getConfig().get(WorkingTreeOptions.KEY);
		}

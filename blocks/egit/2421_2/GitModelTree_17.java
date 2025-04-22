		if (obj == this)
			return true;

		if (obj instanceof GitModelTree && !(obj instanceof GitModelCacheTree)) {
			GitModelTree objTree = (GitModelTree) obj;
			return objTree.location.equals(location)
					&& objTree.baseCommit.equals(baseCommit);
		}

		return false;

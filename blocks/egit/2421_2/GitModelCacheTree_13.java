		if (obj == this)
			return true;

		if (obj instanceof GitModelCacheTree) {
			GitModelCacheTree objTree = (GitModelCacheTree) obj;

			return objTree.getLocation().equals(getLocation())
					&& objTree.getBaseId().equals(getBaseId());
		}

		return false;

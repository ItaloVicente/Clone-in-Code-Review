	@Override
	public boolean equals(Object other) {
		if (other == this)
			return true;

		if (other instanceof GitSyncInfo) {
			GitSynchronizeData otherGsd = ((GitSyncInfo) other).gsd;
			boolean equalGsd = gsd.getProjects().equals(otherGsd.getProjects())
					&& gsd.getRepository().equals(otherGsd.getRepository())
					&& gsd.getDstRevCommit().equals(otherGsd.getDstRevCommit())
					&& gsd.getSrcRevCommit().equals(otherGsd.getSrcRevCommit());

			return super.equals(other) && equalGsd;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return gsd.getSrcRevCommit().hashCode() ^ getLocal().hashCode();
	}


		configuration.setLeftLabel(getFileRevisionLabel(getLeft()));
		configuration.setRightLabel(getFileRevisionLabel(getRight()));
	}

	public String getFullPath() {
		return path.toOSString();
	}

	public boolean isCompareInputFor(Object object) {
		return false;
	}

	@Override
	public int hashCode() {
		int baseHash = 1;
		if (change != null)
			baseHash = change.getObjectId() != null ? change.getObjectId()
				.hashCode() : 31;
		int remoteHash = 11;
		if (change != null)
			remoteHash = change.getRemoteObjectId() != null ? change
				.getRemoteObjectId().hashCode() : 41;

		return baseHash ^ remoteHash ^ path.hashCode();

		return getSrcRefName() == null;
	}

	boolean isAdd() {
		return getAdvertisedRemoteRef() == null;
	}

	boolean isRejected() {
		switch (getStatus()) {
		case REJECTED_NODELETE:
		case REJECTED_NONFASTFORWARD:
		case REJECTED_OTHER_REASON:
		case REJECTED_REMOTE_CHANGED:
			return true;
		default:
			return false;
		}
	}

	boolean isTag() {
		return tag;

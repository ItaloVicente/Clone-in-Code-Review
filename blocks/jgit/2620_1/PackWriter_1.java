	private boolean reuseDeltaFor(ObjectToPack otp) {
		switch (otp.getType()) {
		case Constants.OBJ_COMMIT:
			return reuseDeltaCommits;
		case Constants.OBJ_TREE:
			return true;
		case Constants.OBJ_BLOB:
			return true;
		case Constants.OBJ_TAG:
			return false;
		default:
			return true;
		}
	}


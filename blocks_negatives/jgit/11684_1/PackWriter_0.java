		switch (otp.getType()) {
		case Constants.OBJ_COMMIT:
			return reuseDeltaCommits;
		case Constants.OBJ_TREE:
			return true;
		case Constants.OBJ_BLOB:

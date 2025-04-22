	private String toResultString(ReceiveCommand cmd) {
		switch (cmd.getType()) {
		case CREATE:
			return ReflogEntry.PREFIX_CREATED;
		case UPDATE:
			return isAllowNonFastForwards() ? ReflogEntry.PREFIX_FORCED_UPDATE
					: ReflogEntry.PREFIX_FAST_FORWARD;
		case UPDATE_NONFASTFORWARD:
			return ReflogEntry.PREFIX_FORCED_UPDATE;
		default:
			return null;
		}
	}


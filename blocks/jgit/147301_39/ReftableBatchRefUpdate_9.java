
		if (isAtomic()) {
			if (!ok) {
				pending.stream()
						.filter(cmd -> cmd.getResult() == NOT_ATTEMPTED)
						.forEach(cmd -> cmd.setResult(LOCK_FAILURE));
			}
			return ok;
		}

		for (ReceiveCommand cmd : pending) {
			if (cmd.getResult() == NOT_ATTEMPTED) {
				return true;
			}


		for (ReceiveCommand cmd : pending) {
			if (cmd.getResult() == NOT_ATTEMPTED) {
				return true;
			}
		}

		return false;

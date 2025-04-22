	private static boolean commandsEqual(ReceiveCommand c1
		if (c1 == null || c2 == null) {
			return c1 == c2;
		}
		return c1.getRefName().equals(c2.getRefName())
				&& c1.getOldId().equals(c2.getOldId())
				&& c1.getNewId().equals(c2.getNewId());
	}


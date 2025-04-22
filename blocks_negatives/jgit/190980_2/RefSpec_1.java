		if (!eq(getSource(), b.getSource()))
			return false;
		if (!eq(getDestination(), b.getDestination()))
			return false;
		return true;
	}

	private static boolean eq(String a, String b) {
		if (References.isSameObject(a, b)) {
			return true;

	private ObjectStream getObjectStream(Pair pair
		ObjectStream stream = null;
		try {
			stream = pair.open(side
		} catch (Exception e) {
			stream = null;
		}
		return stream;
	}


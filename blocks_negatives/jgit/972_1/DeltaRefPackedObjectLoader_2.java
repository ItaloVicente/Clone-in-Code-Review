
	@Override
	public int getRawType() {
		return Constants.OBJ_REF_DELTA;
	}

	@Override
	ObjectId getDeltaBase() throws IOException {
		return deltaBase;
	}

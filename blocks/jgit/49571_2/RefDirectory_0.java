	@Override
	public Ref exactRef(String name) throws IOException {
		RefList<Ref> packed = getPackedRefs();
		Ref ref = null;
		try {
			ref = readRef(name
			if (ref != null) {
				ref = resolve(ref
			}
		} catch (IOException e) {
				|| !(e.getCause() instanceof InvalidObjectIdException)) {
				throw e;
			}
		}
		fireRefsChanged();
		return ref;
	}


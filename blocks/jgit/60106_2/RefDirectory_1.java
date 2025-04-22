	@Override
	public Ref exactRef(String name) throws IOException {
		RefList<Ref> packed = getPackedRefs();
		Ref ref;
		try {
			ref = readRef(name
			if (ref != null) {
				ref = resolve(ref
			}
		} catch (IOException e) {
					|| !(e.getCause() instanceof InvalidObjectIdException)) {
				throw e;
			}

			ref = null;
		}
		fireRefsChanged();
		return ref;
	}


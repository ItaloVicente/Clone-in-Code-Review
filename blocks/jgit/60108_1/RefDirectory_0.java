	private Ref readAndResolve(String name
		try {
			Ref ref = readRef(name
			if (ref != null) {
				ref = resolve(ref
			}
			return ref;
		} catch (IOException e) {
					|| !(e.getCause() instanceof InvalidObjectIdException)) {
				throw e;
			}

			return null;
		}
	}

	@Override
	public Ref exactRef(String name) throws IOException {
		Ref ref = readAndResolve(name
		fireRefsChanged();
		return ref;
	}


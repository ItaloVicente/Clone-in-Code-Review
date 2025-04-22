	@Override
	@NonNull
	public Map<String
		try {
			RefList<Ref> packed = getPackedRefs();
			Map<String
			for (String name : refs) {
				Ref ref = readAndResolve(name
				if (ref != null) {
					result.put(name
				}
			}
			return result;
		} finally {
			fireRefsChanged();
		}
	}

	@Override
	@Nullable
	public Ref firstExactRef(String... refs) throws IOException {
		try {
			RefList<Ref> packed = getPackedRefs();
			for (String name : refs) {
				Ref ref = readAndResolve(name
				if (ref != null) {
					return ref;
				}
			}
			return null;
		} finally {
			fireRefsChanged();
		}
	}


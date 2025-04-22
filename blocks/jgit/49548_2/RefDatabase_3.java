	public Ref exactRef(String name) throws IOException {
		int slash = name.lastIndexOf('/');
		Ref result = getRefs(name.substring(0
		if (result != null || slash != -1) {
			return result;
		}

		for (Ref ref : getAdditionalRefs()) {
			if (name.equals(ref.getName())) {
				return ref;
			}
		}
		return null;
	}


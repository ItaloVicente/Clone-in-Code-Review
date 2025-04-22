	public Ref exactRef(String name) throws IOException {
		int slash = name.lastIndexOf('/');
		String prefix = name.substring(0
		String rest = name.substring(slash + 1);
		Ref result = getRefs(prefix).get(rest);
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


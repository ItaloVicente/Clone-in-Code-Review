
		for (Ref ref : getAdditionalRefs()) {
			if (name.equals(ref.getName())) {
				return ref;
			}
		}
		return null;

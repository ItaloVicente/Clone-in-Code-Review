		hashCode = hashCode * 37 + (matchTypeChildren ? 1 : 2);

		for (String attribute : attributes) {
			hashCode = hashCode * 37 + attribute.hashCode();
		}
	}

	public String getType() {
		return type;
	}

	public String[] getAttributes() {
		return attributes;
	}

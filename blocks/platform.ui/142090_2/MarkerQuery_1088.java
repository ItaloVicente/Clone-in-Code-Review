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

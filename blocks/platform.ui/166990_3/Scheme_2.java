	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Scheme [");
		if (name != null) {
			builder.append("name=");
			builder.append(name);
			builder.append(", ");
		}
		if (desription != null) {
			builder.append("desription=");
			builder.append(desription);
		}
		builder.append("]");
		return builder.toString();
	}


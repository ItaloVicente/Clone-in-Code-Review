		return toStringBuilder().toString();
	}

	public String toTextWithSignature() {
		return toStringBuilder().append(signature).toString();
	}

	private StringBuilder toStringBuilder() {

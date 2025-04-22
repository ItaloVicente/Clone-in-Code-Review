	@Override
	public String toString() {
		StringBuilder back = new StringBuilder(position.prefix);
		if (positionReference != null) {
			back.append(positionReference);
		}
		return back.toString();
	}

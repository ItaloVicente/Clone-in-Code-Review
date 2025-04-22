	public String getLabel() {
		return label;
	}

	public MarkerType[] getSubtypes() {
		MarkerType[] types = model.getTypes();
		ArrayList<MarkerType> result = new ArrayList<>();
		for (MarkerType type : types) {

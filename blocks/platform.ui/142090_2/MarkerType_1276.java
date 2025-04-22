	public MarkerType[] getAllSupertypes() {
		ArrayList<MarkerType> result = new ArrayList<>();
		getAllSupertypes(result);
		return result.toArray(new MarkerType[result.size()]);
	}

	private void getAllSupertypes(ArrayList<MarkerType> result) {

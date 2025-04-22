	default public List<IWindowTrim> getAllTrim() {
		ArrayList<IWindowTrim> allTrim = new ArrayList<>();
		for (int areaId : getAreaIds()) {
			allTrim.addAll(getAreaTrim(areaId));
		}
		return allTrim;
	}

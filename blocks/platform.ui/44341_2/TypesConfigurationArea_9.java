	protected List<TypesEntry> elementsForGroup(MarkerFieldFilterGroup group) {
		if (models.containsKey(group)) {
			return models.get(group);
		}
		Iterator<MarkerType> roots = group.getAllTypes().iterator();
		List<TypesEntry> markerNodes = new ArrayList<>();
		HashMap<String, CategoryEntry> categories = new HashMap<>();

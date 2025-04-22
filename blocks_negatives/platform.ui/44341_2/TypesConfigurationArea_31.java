	protected List elementsForGroup(MarkerFieldFilterGroup group) {

		if (models.containsKey(group))
			return (List) models.get(group);
		Iterator roots = group.getAllTypes().iterator();
		List markerNodes = new ArrayList();
		HashMap categories = new HashMap();

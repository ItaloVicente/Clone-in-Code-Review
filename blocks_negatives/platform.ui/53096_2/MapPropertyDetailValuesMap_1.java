	protected Map doGetMap(Object source) {
		Map masterMap = masterProperty.getMap(source);
		Map detailMap = new IdentityMap();
		for (Iterator it = masterMap.entrySet().iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			detailMap.put(entry.getKey(), detailProperty.getValue(entry
					.getValue()));

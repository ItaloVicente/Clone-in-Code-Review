	@SuppressWarnings({ "boxing", "serial" })
	private Map<Integer, List<CommitItem>> getSeverityItems() {
		Map<Integer, List<CommitItem>> result = new HashMap<>();
		for (final CommitItem item : items) {
			if (item.getProblemSeverity() >= IMarker.SEVERITY_WARNING) {
				if (result.get(item.getProblemSeverity()) == null) {
					result.put(item.getProblemSeverity(),
							new ArrayList<CommitItem>() {
								{

									add(item);
								}
							});
				} else {
					result.get(item.getProblemSeverity()).add(item);
				}
			}
		}
		return result;
	}


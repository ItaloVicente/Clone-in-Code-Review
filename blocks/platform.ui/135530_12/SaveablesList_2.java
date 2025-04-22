	public Map<IWorkbenchPart, List<Saveable>> getSaveables(List<IWorkbenchPart> parts) {
		Map<IWorkbenchPart, List<Saveable>> saveablesMap = null;
		if (parts != null && parts.size() > 0) {
			saveablesMap = new HashMap<>();
			for (IWorkbenchPart part : parts) {
				Saveable[] saveables = getSaveables(part);
				if (saveables != null && saveables.length > 0) {
					saveablesMap.put(part, Arrays.asList(saveables));
				}
			}
		}
		return saveablesMap;
	}


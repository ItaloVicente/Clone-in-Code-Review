	private List<QuickAccessElement> getLoadedPreviousPicks() {
		List<QuickAccessElement> previousPicks = previousPicksList.stream().filter(Objects::nonNull)
				.collect(Collectors.toList());
		return previousPicks;
	}


	public void killSlowSearchForReuse() throws SearchForReuseTimeout {
		if(checkSearchForReuseTimeout &&
			Duration.ofSeconds(
				System.currentTimeMillis() - searchForReuseStartTimeEpoc
			).compareTo(searchForReuseTimeout) > 0) {
			throw new SearchForReuseTimeout(searchForReuseTimeout);
		}
	}


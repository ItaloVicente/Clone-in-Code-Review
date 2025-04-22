	public void checkSearchForReuseTimeout() throws SearchForReuseTimeout {
		if (checkSearchForReuseTimeout
				&& Duration.ofMillis(System.currentTimeMillis()
						- searchForReuseStartTimeEpoc)
				.compareTo(searchForReuseTimeout) > 0) {
			throw new SearchForReuseTimeout(searchForReuseTimeout);
		}
	}


	static AbstractRevQueue newDateRevQueue(boolean firstParent) {
		boolean usePriorityQueue = Optional.ofNullable(System.getenv("REVWALK_USE_PRIORITY_QUEUE"))
				.map(Boolean::parseBoolean)
				.orElse(false);

		if(usePriorityQueue) {
			return new DateRevPriorityQueue(firstParent);
		}

		return new DateRevQueue(firstParent);
	}

	static DateRevQueue newDateRevQueue(Generator g) throws IOException {
		boolean usePriorityQueue = Optional.ofNullable(System.getenv("REVWALK_USE_PRIORITY_QUEUE"))
				.map(Boolean::parseBoolean)
				.orElse(false);

		if(usePriorityQueue) {
			return new DateRevPriorityQueue(g);
		}

		return new DateRevQueue(g);
	}


	static AbstractRevQueue newDateRevQueue(boolean firstParent) {
		if(usePriorityQueue()) {
			return new DateRevPriorityQueue(firstParent);
		}

		return new DateRevQueue(firstParent);
	}

	static DateRevQueue newDateRevQueue(Generator g) throws IOException {
		if(usePriorityQueue()) {
			return new DateRevPriorityQueue(g);
		}

		return new DateRevQueue(g);
	}

	private static boolean usePriorityQueue() {
		return Optional.ofNullable(System.getenv("REVWALK_USE_PRIORITY_QUEUE"))
					.map(Boolean::parseBoolean)
					.orElseGet(() ->  Optional.ofNullable(System.getProperty("REVWALK_USE_PRIORITY_QUEUE"))
							.map(Boolean::parseBoolean)
							.orElse(false));
	}


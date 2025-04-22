			if (s == null || s.isEmpty()) {
				continue;
			}
			boolean test;
			if (s.charAt(0) == '!') {
				test = !ResourcePropertyTester.testRepositoryState(repository,
						s.substring(1));
			} else {
				test = ResourcePropertyTester.testRepositoryState(repository,
						s);
			}
			if (!test) {

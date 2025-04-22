			if (repository == null)
				return false;

			for (Object arg : args) {
				String s = (String) arg;
				if (!ResourcePropertyTester.testRepositoryState(repository, s))
					return false;
			}
			return true;

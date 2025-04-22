			if (args != null && args.length > 0) {
				for (Repository repository : repositories) {
					if (!testRepositoryProperties(repository, args)) {
						return false;
					}
				}
			}
			return true;

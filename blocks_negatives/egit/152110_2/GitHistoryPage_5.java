			if (input == null) {
				return;
			}
			Repository db = input.getRepository();
			if (repoHasBeenRemoved(db)) {
				clearHistoryPage();
				return;
			}

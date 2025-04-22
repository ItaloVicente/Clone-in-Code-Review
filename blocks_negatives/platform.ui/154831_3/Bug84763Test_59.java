		contextManagerListener = new IContextManagerListener() {

			@Override
			public void contextManagerChanged(
					ContextManagerEvent contextManagerEvent) {
				previousContextIds = contextManagerEvent
						.getPreviouslyActiveContextIds();
				if (previousContextIds != null) {
					previousContextIds = new HashSet<>(previousContextIds);
				}
			}

		};

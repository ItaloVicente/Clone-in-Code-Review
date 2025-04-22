				if (!hasAlreadyBeenRefreshed) {
					hasAlreadyBeenRefreshed = true;

					if (!getInitialElementSelections().isEmpty()) {
						refreshWithLastSelection = true;

						if (!multi) {
							lastRefreshSelection = new ArrayList<>(asList(getInitialElementSelections().get(0)));
						} else {
							lastRefreshSelection = getInitialElementSelections();
						}
					}
				}

			if (sideBand) {
				try {
					pckIn.discardUntilEnd();
				} catch (IOException e2) {
				}
			}
			fatalError(e.getMessage());

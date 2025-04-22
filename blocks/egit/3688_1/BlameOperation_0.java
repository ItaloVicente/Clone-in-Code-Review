			if (commit == null) {
				if (previous != null) {
					previous.register();
					previous = null;
				}
				continue;
			}

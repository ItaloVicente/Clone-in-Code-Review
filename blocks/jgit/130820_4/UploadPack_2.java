			FirstWant candidate = null;
			try {
				candidate = FirstWant.fromLine(line);
			} catch (PackProtocolException e) {
				try {
					candidate = FirstWant.fromLine(line.substring(0
				} catch (PackProtocolException e1) {
				}
			}

			firstWant = candidate;


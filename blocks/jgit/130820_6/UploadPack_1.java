			FirstWant candidate = null;
			try {
				candidate = FirstWant.fromLine(line);
			} catch (PackProtocolException e) {
			}
			firstWant = candidate;

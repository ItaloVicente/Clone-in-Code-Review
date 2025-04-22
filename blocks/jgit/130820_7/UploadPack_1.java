			FirstWant candidate = null;
			try {
				candidate = FirstWant.fromLine(line);
			} catch (PackProtocolException e) {
				throw new UncheckedIOException(e);
			}
			firstWant = candidate;

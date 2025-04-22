			LongEventInfo eventSnapshot = eventToPublish.getAndSet(null);
			if (eventSnapshot != null) {
				long eventEnd = eventSnapshot.start + eventSnapshot.duration;
				while (numSamples > 0 && eventEnd <= stackSamples[numSamples - 1].getTimestamp()) {
					--numSamples;
				}
				if (numSamples > maxLoggedStackSamples) {

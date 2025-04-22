		} else {
			if (timedout) {
				throw new IllegalArgumentException("Operation has already timed out; ttl " +
								   "specified would allow it to be valid.");
			}
		}

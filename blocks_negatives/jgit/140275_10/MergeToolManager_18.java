		return userDefinedTools;
	}

	/**
	 * @param checkAvailability
	 *            true: for checking if tools can be executed; ATTENTION: this
	 *            check took some time, do not execute often (store the map for
	 *            other actions); false: availability is NOT checked:
	 *            isAvailable() returns default false is this case!
	 * @return the predefined tools with optionally checked availability (long
	 *         running operation)
	 */

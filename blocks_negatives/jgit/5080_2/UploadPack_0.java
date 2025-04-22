	/** Policy the server uses to validate client requests */
	public static enum RequestPolicy {
		/** Client may only ask for objects the server advertised a reference for. */
		ADVERTISED,
		/** Client may ask for any commit reachable from a reference. */
		REACHABLE_COMMIT,
		/** Client may ask for any SHA-1 in the repository. */
		ANY;
	}


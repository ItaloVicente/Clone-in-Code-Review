	/**
	 * Gets an unmodifiable view of the option strings associated with the push.
	 *
	 * @return an unmodifiable view of pushOptions, or null (if pushOptions is).
	 * @throws IllegalStateException
	 *             if allowPushOptions has not been set to true.
	 * @throws RequestNotYetReadException
	 *             if the client's request has not yet been read from the wire,
	 *             so we do not know if they expect push options. Note that the
	 *             client may have already written the request, it just has not
	 *             been read.
	 * @since 4.5
	 */
	@Nullable
	public List<String> getPushOptions() {
		if (!allowPushOptions) {
			throw new IllegalStateException();
		}
		if (enabledCapabilities == null) {
			throw new RequestNotYetReadException();
		}
		if (pushOptions == null) {
			return null;
		}
		return Collections.unmodifiableList(pushOptions);
	}


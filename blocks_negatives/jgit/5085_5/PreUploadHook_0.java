	/**
	 * Invoked just before {@link UploadPack#sendAdvertisedRefs(RefAdvertiser)}.
	 * <p>
	 * New code should prefer implementing
	 * {@link AdvertiseRefsHook#advertiseRefs(UploadPack)}, which is more powerful
	 * and may replace this method in the future.
	 *
	 * @param up
	 *            the upload pack instance handling the connection.
	 * @throws ServiceMayNotContinueException
	 *             abort; the message will be sent to the user.
	 */
	public void onPreAdvertiseRefs(UploadPack up)
			throws ServiceMayNotContinueException;


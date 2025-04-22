	/**
	 * Invoked just before {@link UploadPack#sendAdvertisedRefs(RefAdvertiser)}.
	 *
	 * @param up
	 *            the upload pack instance handling the connection.
	 * @throws ServiceMayNotContinueException
	 *             abort; the message will be sent to the user.
	 */
	public void onPreAdvertiseRefs(UploadSession up)
			throws ServiceMayNotContinueException;


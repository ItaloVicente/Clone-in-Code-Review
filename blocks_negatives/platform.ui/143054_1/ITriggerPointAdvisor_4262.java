	 * @param triggerPoint
	 *            the trigger point to test
	 * @param identifier
	 *            the identifier to test against the trigger point
	 * @return the set of activities that should be enabled if this the
	 *         contribution represented by this identifier is to be used. If
	 *         this is not <code>null</code>, the caller can proceed with
	 *         usage of the contribution provided that the collection of
	 *         activities is enabled. If this is <code>null</code>, the
	 *         caller should assume that the operation involving the
	 *         contribution should be aborted. If this method returns the empty
	 *         set then the operation can proceed without any changes to
	 *         activity enablement state. Please note that it is the callers
	 *         responsibility to ensure that the Set returned by this method is
	 *         actually enabled - after setting the enabled state of the
	 *         required activities the change should be verified by consulting

	 * @param path                  of the entry
	 * @param entry                 to add
	 * @param cleanupStreamType     to use for the cleanup metadata
	 * @param cleanupSmudgeCommand  to use for the cleanup metadata
	 * @param checkoutStreamType    to use for the checkout metadata
	 * @param checkoutSmudgeCommand to use for the checkout metadata
	 * @since 6.1
	 */
	public void addToCheckout(
			String path, DirCacheEntry entry, EolStreamType cleanupStreamType,
			String cleanupSmudgeCommand, EolStreamType checkoutStreamType, String checkoutSmudgeCommand) {

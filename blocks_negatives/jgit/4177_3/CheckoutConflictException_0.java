	/**
	 * Construct a CheckoutConflictException for the specified file
	 *
	 * @param file
	 */
	public CheckoutConflictException(String file) {
		super(MessageFormat.format(JGitText.get().checkoutConflictWithFile, file));
	}

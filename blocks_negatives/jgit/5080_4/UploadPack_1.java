	/**
	 * Get the PackWriter's statistics if a pack was sent to the client.
	 *
	 * @return statistics about pack output, if a pack was sent. Null if no pack
	 *         was sent, such as during the negotation phase of a smart HTTP
	 *         connection, or if the client was already up-to-date.
	 */

	 * Get the minimum update index for log entries that appear in this
	 * reftable.
	 *
	 * @return the minimum update index for log entries that appear in this
	 *         reftable. This should be 1 higher than the prior reftable's
	 *         {@code maxUpdateIndex} if this table is used in a stack.
	 * @throws java.io.IOException
	 *             file cannot be read.

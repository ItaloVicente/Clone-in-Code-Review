	 * @param supplier
	 *            supplier which returns null if the side-effect should continue
	 *            to wait or returns a non-null value to be passed to the
	 *            consumer if it is time to invoke the consumer
	 * @param consumer
	 *            a (possibly non-idempotent) consumer which will receive the
	 *            first non-null result returned by the supplier.
	 * @return a side-effect which can be used to control this operation. If it
	 *         is disposed before the consumer is invoked, the consumer will
	 *         never be invoked. It will not invoke the supplier if it is
	 *         paused.

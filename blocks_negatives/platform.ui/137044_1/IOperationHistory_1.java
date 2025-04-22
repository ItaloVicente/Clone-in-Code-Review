	 * <p>
	 * The severity code in the returned status describes whether the operation
	 * succeeded and whether it remains in the history. <code>OK</code>
	 * severity indicates that the redo operation was successful, and (since
	 * release 3.2), that the operation will be placed in the undo history.
	 * (Prior to 3.2, a successfully redone operation would not be placed on the
	 * undo history if it could not be undone. Since 3.2, this is relaxed, and
	 * all successfully redone operations are placed in the undo history.)
	 * Listeners will receive the <code>REDONE</code> notification.
	 * </p>
	 * <p>
	 * Other severity codes (<code>CANCEL</code>, <code>ERROR</code>,
	 * <code>INFO</code>, etc.) are not specifically interpreted by the
	 * history. The operation will remain in the history and the returned status
	 * is simply passed back to the caller. For all severities other than <code>OK</code>,
	 * listeners will receive the <code>OPERATION_NOT_OK</code> notification
	 * instead of the <code>REDONE</code> notification if the redo was
	 * approved and attempted.
	 * </p>
	 *
	 * @throws ExecutionException
	 *             if an exception occurred during redo.

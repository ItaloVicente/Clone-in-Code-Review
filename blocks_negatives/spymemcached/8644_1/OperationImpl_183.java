	/**
	 * Match the status line provided against one of the given
	 * OperationStatus objects.  If none match, return a failure status with
	 * the given line.
	 *
	 * @param line the current line
	 * @param statii several status objects
	 * @return the appropriate status object
	 */
	protected final OperationStatus matchStatus(String line,
			OperationStatus... statii) {
		OperationStatus rv=null;
		for(OperationStatus status : statii) {
			if(line.equals(status.getMessage())) {
				rv=status;
			}
		}
		if(rv == null) {
			rv=new OperationStatus(false, line);
		}
		return rv;
	}

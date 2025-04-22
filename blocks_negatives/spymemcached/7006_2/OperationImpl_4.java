	protected static final OperationStatus NOT_FOUND_STATUS =
		new CASOperationStatus(false, "Not Found", CASResponse.NOT_FOUND);
	protected static final OperationStatus EXISTS_STATUS =
		new CASOperationStatus(false, "Object exists", CASResponse.EXISTS);
	protected static final OperationStatus NOT_STORED_STATUS =
		new CASOperationStatus(false, "Not Stored", CASResponse.NOT_FOUND);
	protected static final OperationStatus NOT_MY_VBUCKET_STATUS =
		new OperationStatus(false, "Not my vbucket");
	protected static final OperationStatus TEMP_FAIL =
		new OperationStatus(false, "Temporary Error");


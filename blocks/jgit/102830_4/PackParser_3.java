			BlobObjectChecker checker = null;
			if (objCheck != null) {
				checker = objCheck.newBlobObjectChecker();
			}
			if (checker == null) {
				checker = BlobObjectChecker.NULL_CHECKER;
			}

			EolStreamType streamType = EolStreamTypeUtil.detectStreamType(
					OperationType.CHECKIN_OP
					tw.getAttributes());
			long blobLen = len == 0 ? 0
					: getEntryContentLength(mergedFile
			try (InputStream is = EolStreamTypeUtil.wrapInputStream(
					new FileInputStream(mergedFile)
				dce.setObjectId(
						getObjectInserter().insert(OBJ_BLOB

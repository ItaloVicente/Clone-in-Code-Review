	private ObjectId insertBlob(ObjectInserter inserter
			WorkingTreeIterator f
			throws FilterFailedException
		InputStream contentStream;
		long contentLength = f.getEntryContentLength();
		entry.setLastModified(f.getEntryLastModified());
		entry.setLength(contentLength);
		TemporaryBuffer attributeProcessedContent = f.processFilter(
				Constants.ATTR_FILTER_TYPE_CLEAN
		if (attributeProcessedContent != null) {
			contentLength = attributeProcessedContent.length();
			contentStream = attributeProcessedContent.openInputStream();
		} else {
			contentStream = f.openEntryStream();
		}

		try {
			return inserter.insert(Constants.OBJ_BLOB
					contentStream);
		} finally {
			contentStream.close();
		}
	}


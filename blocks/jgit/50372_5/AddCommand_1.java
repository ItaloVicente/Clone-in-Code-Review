	private ObjectId insertBlob(ObjectInserter inserter
			WorkingTreeIterator f
			throws FilterFailedException
		long contentLength = f.getEntryContentLength();
		entry.setLastModified(f.getEntryLastModified());
		entry.setLength(contentLength);
		TemporaryBuffer attributeProcessedContent = f.processFilter(
				Constants.ATTR_FILTER_TYPE_CLEAN
		if (attributeProcessedContent != null) {
			contentLength = attributeProcessedContent.length();
		}

		try (InputStream contentStream = (attributeProcessedContent == null)
				? f.openEntryStream()
				: attributeProcessedContent.openInputStream()) {
			return inserter.insert(Constants.OBJ_BLOB
					contentStream);
		}
	}


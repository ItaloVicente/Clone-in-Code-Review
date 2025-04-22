			InputStream filteredInputStream = objectInputStream;
			if (metadata != null) {
				filteredInputStream = Filtering.filter(db, path,
						objectInputStream, metadata.smudgeFilterCommand);
			}
			EolStreamType streamType;
			if (metadata != null && metadata.eolStreamType != null) {
				streamType = metadata.eolStreamType;
			} else if (workingTreeOptions.getAutoCRLF() == AutoCRLF.TRUE) {
				streamType = EolStreamType.AUTO_CRLF;
			} else {
				streamType = EolStreamType.DIRECT;

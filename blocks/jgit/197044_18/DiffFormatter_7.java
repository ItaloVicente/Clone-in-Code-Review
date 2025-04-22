	    if (id.isComplete() && reader != null) {
		try {
		    if (asBinary) {
			id = reader.abbreviate(id.toObjectId()
				OBJECT_ID_STRING_LENGTH);
		    } else {
			id = reader.abbreviate(id.toObjectId()
				abbreviationLength);
		    }
		} catch (IOException cannotAbbreviate) {

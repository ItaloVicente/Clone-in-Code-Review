		if (Constants.HEAD.equals(ref)) {
		} else if ("FETCH_HEAD".equals(ref)) {
		} else if ("MERGE_HEAD".equals(ref)) {
		} else if (ref.startsWith(Constants.R_REFS)) {
		} else
			ref = Constants.R_HEADS + ref;


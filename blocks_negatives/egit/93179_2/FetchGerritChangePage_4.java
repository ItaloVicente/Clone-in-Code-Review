		if (doChangeBranch) {
			Ref localRef = getLocalRef(spec.getSource());
			checkout(localRef.getName(), monitor);
		} else {
			try {
				RevCommit commit = fetchChange(uri, spec, monitor);

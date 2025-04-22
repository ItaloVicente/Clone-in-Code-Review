		List<MApplicationElement> merged = processModelFragment(fragment, contributorURI, checkExist);
		if (merged.size() > 0) {
			evalImports = true;
			addedElements.addAll(merged);
		} else {
			logger.debug("Nothing to merge for fragment \"{0}\" of \"{1}\"", contributorURI, //$NON-NLS-1$
					contributorName);

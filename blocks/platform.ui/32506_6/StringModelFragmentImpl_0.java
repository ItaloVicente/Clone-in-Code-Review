		String idsOrXPath = getParentElementId();
		if (idsOrXPath.startsWith("xpath:")) {
			String xPath = idsOrXPath.substring(6);
			mergeXPath(application, ret, xPath);
		} else {
			mergeIdList(application, ret, idsOrXPath);

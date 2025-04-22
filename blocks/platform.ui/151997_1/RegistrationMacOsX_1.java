			Set<String> locations = determineHandlerLocations(lsRegisterEntries, scheme.getName());
			if (locations.isEmpty()) {
				SchemeInformation schemeInfo = new SchemeInformation(scheme.getName(), scheme.getDescription());
				schemeInfo.setHandlerLocation(""); //$NON-NLS-1$
				returnList.add(schemeInfo);
			} else {
				SchemeInformation schemeInfo = new SchemeInformation(scheme.getName(),

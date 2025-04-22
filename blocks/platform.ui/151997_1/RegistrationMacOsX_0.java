		List<ISchemeInformation> schemesInformation = getSchemesInformation(toAdd);
		for (ISchemeInformation schemeInformation : schemesInformation) {
			String handlerInstanceLocation = schemeInformation.getHandlerInstanceLocation();
			processExecutor.execute(LSREGISTER, UNREGISTER, handlerInstanceLocation);
		}


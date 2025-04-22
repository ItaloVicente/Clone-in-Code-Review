		List<MApplicationElement> targetElements;
		if ("/".equals(xPath)) {
			targetElements = Collections.singletonList(application);
		} else {
			XPathContextFactory<EObject> f = EcoreXPathContextFactory.newInstance();
			XPathContext xpathContext = f.newContext((EObject) application);
			Iterator<Object> i = xpathContext.iterate(xPath);

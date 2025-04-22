		ArrayList<MApplicationElement> ret = new ArrayList<MApplicationElement>();
		
		String idsOrXPath = getParentElementId();
		if (idsOrXPath.startsWith("xpath:")){
			idsOrXPath = idsOrXPath.substring(6);
			XPathContextFactory<EObject> f = EcoreXPathContextFactory.newInstance();
			XPathContext xpathContext = f.newContext((EObject)application);
			Iterator<Object> i = xpathContext.iterate(idsOrXPath);

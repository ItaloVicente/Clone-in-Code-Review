		XPathContextFactory<EObject> f = EcoreXPathContextFactory.newInstance();
		XPathContext xpathContext = f.newContext((EObject) application);
		Iterator<Object> i = xpathContext.iterate(xPath);

		List<MApplicationElement> targetElements = new ArrayList<>();
		try {
			while (i.hasNext()) {
				Object obj = i.next();
				if (obj instanceof MApplicationElement) {
					MApplicationElement o = (MApplicationElement) obj;
					targetElements.add(o);

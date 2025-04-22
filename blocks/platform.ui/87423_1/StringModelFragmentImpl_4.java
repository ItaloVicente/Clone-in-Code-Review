	public List<EClass> getTargetClass(MApplication application) {
		List<EClass> ret = Collections.emptyList();

		String idsOrXPath = getParentElementId();
		if ("xpath:/".equals(idsOrXPath) || "org.eclipse.e4.legacy.ide.application".equals(idsOrXPath)) {
			ret = new ArrayList<EClass>();
			ret.add(ApplicationPackageImpl.eINSTANCE.getApplication());
		} else {
			if (idsOrXPath.startsWith("xpath:")) {
				String xPath = idsOrXPath.substring(6);
				ret = getTargetClassFromXPath(application, xPath);
			} else {

				MApplicationElement o = ModelUtils.findElementById(application, idsOrXPath);
				if (o != null) {
					ret = new ArrayList<EClass>();
					ret.add(((EObject) o).eClass());
				}
			}
		}

		return ret;

	}

	private List<EClass> getTargetClassFromXPath(MApplication application, String xpath) {
		List<EClass> ret = new ArrayList<EClass>();

		XPathContextFactory<EObject> f = EcoreXPathContextFactory.newInstance();
		XPathContext xpathContext = f.newContext((EObject) application);
		Iterator<Object> i = xpathContext.iterate(xpath);

		try {
			while (i.hasNext()) {
				Object obj = i.next();
				if (obj instanceof MApplicationElement) {
					ApplicationElementImpl ae = (ApplicationElementImpl) obj;
					ret.add(ae.eClass());
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return ret;
	}

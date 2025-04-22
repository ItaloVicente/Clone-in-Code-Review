	private void mergeXPath(MApplication application, List<MApplicationElement> ret, String xPath) {

		XPathContextFactory<EObject> f = EcoreXPathContextFactory.newInstance();
		XPathContext xpathContext = f.newContext((EObject) application);
		Iterator<Object> i = xpathContext.iterate(xPath);

		List<MApplicationElement> targetElements = new ArrayList<MApplicationElement>();
		try {
			while (i.hasNext()) {
				Object obj = i.next();
				if (obj instanceof MApplicationElement) {
					MApplicationElement o = (MApplicationElement) obj;
					targetElements.add(o);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		for (MApplicationElement targetElement : targetElements) {
			EStructuralFeature feature = ((EObject) targetElement).eClass().getEStructuralFeature(getFeaturename());
			List<MApplicationElement> elements;
			elements = new ArrayList<MApplicationElement>();
			for (MApplicationElement element : getElements()) {
				elements.add((MApplicationElement) EcoreUtil.copy((EObject) element));
			}
			if (elements.isEmpty() == false) {
				ret.addAll(ModelUtils.merge(targetElement, feature, elements, getPositionInList()));
			}
		}

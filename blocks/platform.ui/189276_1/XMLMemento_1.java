=======
		String errorMessage = null;
		Exception exception = null;
		DocumentBuilderFactory factory = null;
		Object attributeDTDOldValue = null;
		Object attributeSchemaOldValue = null;
		try {
			factory = DocumentBuilderFactory.newInstance();
			try {
				attributeDTDOldValue = factory.getAttribute(javax.xml.XMLConstants.ACCESS_EXTERNAL_DTD);
				attributeSchemaOldValue = factory.getAttribute(javax.xml.XMLConstants.ACCESS_EXTERNAL_SCHEMA);
			} catch (NullPointerException | IllegalArgumentException e) {
			}
			factory.setAttribute(javax.xml.XMLConstants.ACCESS_EXTERNAL_DTD,
					getAttributeNewValue(attributeDTDOldValue));
			factory.setAttribute(javax.xml.XMLConstants.ACCESS_EXTERNAL_SCHEMA,
					getAttributeNewValue(attributeSchemaOldValue));
			DocumentBuilder parser = factory.newDocumentBuilder();
			InputSource source = new InputSource(reader);
			if (baseDir != null) {
>>>>>>> CHANGE (d31e90 Bug 577894 - Security Issue -- XXE Attack)

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

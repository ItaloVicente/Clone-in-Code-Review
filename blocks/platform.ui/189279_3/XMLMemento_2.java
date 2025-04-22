		} finally {
			if (factory != null) {
				factory.setAttribute(javax.xml.XMLConstants.ACCESS_EXTERNAL_DTD, attributeDTDOldValue);
				factory.setAttribute(javax.xml.XMLConstants.ACCESS_EXTERNAL_SCHEMA, attributeSchemaOldValue);
			}
		}

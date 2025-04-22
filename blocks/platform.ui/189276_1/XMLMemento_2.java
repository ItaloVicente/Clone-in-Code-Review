=======
			}
		} catch (ParserConfigurationException e) {
			exception = e;
			errorMessage = WorkbenchMessages.XMLMemento_parserConfigError;
		} catch (IOException e) {
			exception = e;
			errorMessage = WorkbenchMessages.XMLMemento_ioError;
		} catch (SAXException e) {
			exception = e;
			errorMessage = WorkbenchMessages.XMLMemento_formatError;
		} finally {
			if (factory != null) {
				factory.setAttribute(javax.xml.XMLConstants.ACCESS_EXTERNAL_DTD, attributeDTDOldValue);
				factory.setAttribute(javax.xml.XMLConstants.ACCESS_EXTERNAL_SCHEMA, attributeSchemaOldValue);
			}
		}
>>>>>>> CHANGE (d31e90 Bug 577894 - Security Issue -- XXE Attack)

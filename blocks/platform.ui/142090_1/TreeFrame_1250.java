		for (IMemento currentMemento : elementMem) {
			String factoryID = currentMemento.getString(TAG_FACTORY_ID);
			if (factoryID != null) {
				IElementFactory factory = PlatformUI.getWorkbench()
						.getElementFactory(factoryID);
				if (factory != null) {

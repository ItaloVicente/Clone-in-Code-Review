        for (IMemento element : elementMem) {
            String factoryID = element.getString(TAG_FACTORY_ID);
            if (factoryID != null) {
                IElementFactory factory = PlatformUI.getWorkbench()
                        .getElementFactory(factoryID);
                if (factory != null) {

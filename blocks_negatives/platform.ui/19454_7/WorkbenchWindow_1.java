		MToolControl spacerGlueControl = (MToolControl) modelService.find("Spacer Glue", model); //$NON-NLS-1$
		if (spacerGlueControl == null) {
			spacerGlueControl = MenuFactoryImpl.eINSTANCE.createToolControl();
			spacerGlueControl
			spacerGlueControl.getTags().add(TrimBarLayout.GLUE);
			trimBar.getChildren().add(spacerGlueControl);
		}

		MToolControl searchControl = (MToolControl) modelService.find("SearchField", model); //$NON-NLS-1$
		if (searchControl == null) {
			searchControl = MenuFactoryImpl.eINSTANCE.createToolControl();
			searchControl
			trimBar.getChildren().add(searchControl);
		}

		MToolControl glueControl = (MToolControl) modelService.find("Search-PS Glue", model); //$NON-NLS-1$
		if (glueControl == null) {
			glueControl = MenuFactoryImpl.eINSTANCE.createToolControl();
			glueControl
			glueControl.getTags().add(TrimBarLayout.GLUE);
			trimBar.getChildren().add(glueControl);
		}


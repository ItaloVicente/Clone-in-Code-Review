		EGenericType g1 = createEGenericType(this.getPart());
		compositePartEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theUiPackage.getGenericTile());
		EGenericType g2 = createEGenericType(this.getPartSashContainerElement());
		g1.getETypeArguments().add(g2);
		compositePartEClass.getEGenericSuperTypes().add(g1);

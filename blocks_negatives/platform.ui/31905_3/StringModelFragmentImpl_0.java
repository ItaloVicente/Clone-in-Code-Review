		MApplicationElement o =  ModelUtils.findElementById(application, getParentElementId());
		if( o != null ) {
			EStructuralFeature feature = ((EObject)o).eClass().getEStructuralFeature(getFeaturename());
			if( feature != null ) {
				return ModelUtils.merge(o, feature, getElements(), getPositionInList());	
			}

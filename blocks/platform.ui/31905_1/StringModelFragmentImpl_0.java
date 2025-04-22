		ArrayList<MApplicationElement> ret = new ArrayList<MApplicationElement>();
		String[] parentIds = getParentElementId().split("\\s*\\|\\s*");
		for (String parentId : parentIds){
			MApplicationElement o =  ModelUtils.findElementById(application, parentId);
			if( o != null ) {
				EStructuralFeature feature = ((EObject)o).eClass().getEStructuralFeature(getFeaturename());
				if( feature != null ) {
					List<MApplicationElement> elements;
					if (parentIds.length > 1){
						elements = new ArrayList<MApplicationElement>();
						for (MApplicationElement element : getElements()){
							elements.add((MApplicationElement) EcoreUtil.copy((EObject) element));
						}
					}else{
						elements = getElements();
					}
					ret.addAll(ModelUtils.merge(o, feature, elements, getPositionInList()));	
				}		


		EClassProvider eclassProvider = appContext.get(EClassProvider.class); // get an instance of the OSGi-Service EClassProvider (always get it here; reason: Dynamic-Awareness)
		if( eclassProvider != null ){
			EClass eClass = eclassProvider.getEClass(elementType);
	
			if (eClass != null) {
				checkInstantiation(eClass); // to have a better exception message if an EClass is abstract or an interface (otherwise EcoreUitl#create() will throw a generic one, i.e.: 'The class '...' is not a valid classifier')
				checkDeprecation(eClass); // just for fun because Jonas thought about deprecated classes
	
				return (T) EcoreUtil.create(eClass);
			}
		}


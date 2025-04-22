			case BasicPackageImpl.PART_DESCRIPTOR__VARIABLES:
				getVariables().clear();
				getVariables().addAll((Collection<? extends String>)newValue);
				return;
			case BasicPackageImpl.PART_DESCRIPTOR__PROPERTIES:
				((EStructuralFeature.Setting)((EMap.InternalMapView<String, String>)getProperties()).eMap()).set(newValue);
				return;

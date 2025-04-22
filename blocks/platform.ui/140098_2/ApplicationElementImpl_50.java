		case ApplicationPackageImpl.APPLICATION_ELEMENT__ELEMENT_ID:
			setElementId((String) newValue);
			return;
		case ApplicationPackageImpl.APPLICATION_ELEMENT__PERSISTED_STATE:
			((EStructuralFeature.Setting) ((EMap.InternalMapView<String, String>) getPersistedState()).eMap())
					.set(newValue);
			return;
		case ApplicationPackageImpl.APPLICATION_ELEMENT__TAGS:
			getTags().clear();
			getTags().addAll((Collection<? extends String>) newValue);
			return;
		case ApplicationPackageImpl.APPLICATION_ELEMENT__CONTRIBUTOR_URI:
			setContributorURI((String) newValue);
			return;
		case ApplicationPackageImpl.APPLICATION_ELEMENT__TRANSIENT_DATA:
			((EStructuralFeature.Setting) ((EMap.InternalMapView<String, Object>) getTransientData()).eMap())
					.set(newValue);
			return;

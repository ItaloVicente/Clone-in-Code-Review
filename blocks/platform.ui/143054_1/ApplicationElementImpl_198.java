		case ApplicationPackageImpl.APPLICATION_ELEMENT__ELEMENT_ID:
			return getElementId();
		case ApplicationPackageImpl.APPLICATION_ELEMENT__PERSISTED_STATE:
			if (coreType)
				return ((EMap.InternalMapView<String, String>) getPersistedState()).eMap();
			else
				return getPersistedState();
		case ApplicationPackageImpl.APPLICATION_ELEMENT__TAGS:
			return getTags();
		case ApplicationPackageImpl.APPLICATION_ELEMENT__CONTRIBUTOR_URI:
			return getContributorURI();
		case ApplicationPackageImpl.APPLICATION_ELEMENT__TRANSIENT_DATA:
			if (coreType)
				return ((EMap.InternalMapView<String, Object>) getTransientData()).eMap();
			else
				return getTransientData();

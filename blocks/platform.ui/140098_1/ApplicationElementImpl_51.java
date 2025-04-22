		case ApplicationPackageImpl.APPLICATION_ELEMENT__ELEMENT_ID:
			setElementId(ELEMENT_ID_EDEFAULT);
			return;
		case ApplicationPackageImpl.APPLICATION_ELEMENT__PERSISTED_STATE:
			getPersistedState().clear();
			return;
		case ApplicationPackageImpl.APPLICATION_ELEMENT__TAGS:
			getTags().clear();
			return;
		case ApplicationPackageImpl.APPLICATION_ELEMENT__CONTRIBUTOR_URI:
			setContributorURI(CONTRIBUTOR_URI_EDEFAULT);
			return;
		case ApplicationPackageImpl.APPLICATION_ELEMENT__TRANSIENT_DATA:
			getTransientData().clear();
			return;

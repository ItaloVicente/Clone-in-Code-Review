			case ApplicationPackageImpl.APPLICATION_ELEMENT__ELEMENT_ID:
				return ELEMENT_ID_EDEFAULT == null ? elementId != null : !ELEMENT_ID_EDEFAULT.equals(elementId);
			case ApplicationPackageImpl.APPLICATION_ELEMENT__PERSISTED_STATE:
				return persistedState != null && !persistedState.isEmpty();
			case ApplicationPackageImpl.APPLICATION_ELEMENT__TAGS:
				return tags != null && !tags.isEmpty();
			case ApplicationPackageImpl.APPLICATION_ELEMENT__CONTRIBUTOR_URI:
				return CONTRIBUTOR_URI_EDEFAULT == null ? contributorURI != null : !CONTRIBUTOR_URI_EDEFAULT.equals(contributorURI);
			case ApplicationPackageImpl.APPLICATION_ELEMENT__TRANSIENT_DATA:
				return transientData != null && !transientData.isEmpty();

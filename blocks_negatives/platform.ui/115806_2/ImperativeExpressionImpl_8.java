		case UiPackageImpl.IMPERATIVE_EXPRESSION__CONTRIBUTION_URI:
			return CONTRIBUTION_URI_EDEFAULT == null ? contributionURI != null : !CONTRIBUTION_URI_EDEFAULT.equals(contributionURI);
		case UiPackageImpl.IMPERATIVE_EXPRESSION__OBJECT:
			return OBJECT_EDEFAULT == null ? object != null : !OBJECT_EDEFAULT.equals(object);
		case UiPackageImpl.IMPERATIVE_EXPRESSION__TRACKING:
			return tracking != TRACKING_EDEFAULT;

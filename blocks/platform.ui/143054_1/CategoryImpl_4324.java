		case CommandsPackageImpl.CATEGORY__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		case CommandsPackageImpl.CATEGORY__DESCRIPTION:
			return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
		case CommandsPackageImpl.CATEGORY__LOCALIZED_NAME:
			return LOCALIZED_NAME_EDEFAULT == null ? getLocalizedName() != null
					: !LOCALIZED_NAME_EDEFAULT.equals(getLocalizedName());
		case CommandsPackageImpl.CATEGORY__LOCALIZED_DESCRIPTION:
			return LOCALIZED_DESCRIPTION_EDEFAULT == null ? getLocalizedDescription() != null
					: !LOCALIZED_DESCRIPTION_EDEFAULT.equals(getLocalizedDescription());

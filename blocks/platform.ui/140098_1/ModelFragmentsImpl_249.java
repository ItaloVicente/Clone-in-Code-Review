		case FragmentPackageImpl.MODEL_FRAGMENTS__IMPORTS:
			getImports().clear();
			getImports().addAll((Collection<? extends MApplicationElement>) newValue);
			return;
		case FragmentPackageImpl.MODEL_FRAGMENTS__FRAGMENTS:
			getFragments().clear();
			getFragments().addAll((Collection<? extends MModelFragment>) newValue);
			return;

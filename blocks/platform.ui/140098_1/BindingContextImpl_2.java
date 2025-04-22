		case CommandsPackageImpl.BINDING_CONTEXT__NAME:
			setName(NAME_EDEFAULT);
			return;
		case CommandsPackageImpl.BINDING_CONTEXT__DESCRIPTION:
			setDescription(DESCRIPTION_EDEFAULT);
			return;
		case CommandsPackageImpl.BINDING_CONTEXT__CHILDREN:
			getChildren().clear();
			return;

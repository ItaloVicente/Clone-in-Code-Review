			case CommandsPackageImpl.BINDING_CONTEXT__NAME:
				setName((String)newValue);
				return;
			case CommandsPackageImpl.BINDING_CONTEXT__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case CommandsPackageImpl.BINDING_CONTEXT__CHILDREN:
				getChildren().clear();
				getChildren().addAll((Collection<? extends MBindingContext>)newValue);
				return;

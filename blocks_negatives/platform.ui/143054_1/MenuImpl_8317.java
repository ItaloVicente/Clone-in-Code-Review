			case MenuPackageImpl.MENU__CHILDREN:
				getChildren().clear();
				getChildren().addAll((Collection<? extends MMenuElement>)newValue);
				return;
			case MenuPackageImpl.MENU__SELECTED_ELEMENT:
				setSelectedElement((MMenuElement)newValue);
				return;
			case MenuPackageImpl.MENU__ENABLED:
				setEnabled((Boolean)newValue);
				return;

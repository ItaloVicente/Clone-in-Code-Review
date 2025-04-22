			case UiPackageImpl.ELEMENT_CONTAINER__CHILDREN:
				getChildren().clear();
				return;
			case UiPackageImpl.ELEMENT_CONTAINER__SELECTED_ELEMENT:
				setSelectedElement((T)null);
				return;

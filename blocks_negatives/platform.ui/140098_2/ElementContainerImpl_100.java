			case UiPackageImpl.ELEMENT_CONTAINER__CHILDREN:
				getChildren().clear();
				getChildren().addAll((Collection<? extends T>)newValue);
				return;
			case UiPackageImpl.ELEMENT_CONTAINER__SELECTED_ELEMENT:
				setSelectedElement((T)newValue);
				return;

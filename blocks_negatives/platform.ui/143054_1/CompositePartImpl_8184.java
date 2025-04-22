			case BasicPackageImpl.COMPOSITE_PART__CHILDREN:
				getChildren().clear();
				return;
			case BasicPackageImpl.COMPOSITE_PART__SELECTED_ELEMENT:
				setSelectedElement((MPartSashContainerElement)null);
				return;
			case BasicPackageImpl.COMPOSITE_PART__HORIZONTAL:
				setHorizontal(HORIZONTAL_EDEFAULT);
				return;

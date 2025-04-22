			case BasicPackageImpl.COMPOSITE_PART__CHILDREN:
				getChildren().clear();
				getChildren().addAll((Collection<? extends MPartSashContainerElement>)newValue);
				return;
			case BasicPackageImpl.COMPOSITE_PART__SELECTED_ELEMENT:
				setSelectedElement((MPartSashContainerElement)newValue);
				return;
			case BasicPackageImpl.COMPOSITE_PART__HORIZONTAL:
				setHorizontal((Boolean)newValue);
				return;

		case AdvancedPackageImpl.PERSPECTIVE_STACK__CHILDREN:
			getChildren().clear();
			return;
		case AdvancedPackageImpl.PERSPECTIVE_STACK__SELECTED_ELEMENT:
			setSelectedElement((MPerspective) null);
			return;

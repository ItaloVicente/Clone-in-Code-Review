		case AdvancedPackageImpl.PERSPECTIVE_STACK__CHILDREN:
			getChildren().clear();
			getChildren().addAll((Collection<? extends MPerspective>) newValue);
			return;
		case AdvancedPackageImpl.PERSPECTIVE_STACK__SELECTED_ELEMENT:
			setSelectedElement((MPerspective) newValue);
			return;

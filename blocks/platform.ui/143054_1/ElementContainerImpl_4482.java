		case UiPackageImpl.ELEMENT_CONTAINER__CHILDREN:
			return getChildren();
		case UiPackageImpl.ELEMENT_CONTAINER__SELECTED_ELEMENT:
			if (resolve)
				return getSelectedElement();
			return basicGetSelectedElement();

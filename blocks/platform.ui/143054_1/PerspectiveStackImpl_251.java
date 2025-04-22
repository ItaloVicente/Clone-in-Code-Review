		case AdvancedPackageImpl.PERSPECTIVE_STACK__CHILDREN:
			return getChildren();
		case AdvancedPackageImpl.PERSPECTIVE_STACK__SELECTED_ELEMENT:
			if (resolve)
				return getSelectedElement();
			return basicGetSelectedElement();

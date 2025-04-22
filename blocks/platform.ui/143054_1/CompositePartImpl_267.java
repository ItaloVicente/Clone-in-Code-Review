		case BasicPackageImpl.COMPOSITE_PART__CHILDREN:
			return getChildren();
		case BasicPackageImpl.COMPOSITE_PART__SELECTED_ELEMENT:
			if (resolve)
				return getSelectedElement();
			return basicGetSelectedElement();
		case BasicPackageImpl.COMPOSITE_PART__HORIZONTAL:
			return isHorizontal();

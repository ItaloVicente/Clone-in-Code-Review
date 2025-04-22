			case BasicPackageImpl.COMPOSITE_PART__CHILDREN:
				return children != null && !children.isEmpty();
			case BasicPackageImpl.COMPOSITE_PART__SELECTED_ELEMENT:
				return selectedElement != null;
			case BasicPackageImpl.COMPOSITE_PART__HORIZONTAL:
				return horizontal != HORIZONTAL_EDEFAULT;

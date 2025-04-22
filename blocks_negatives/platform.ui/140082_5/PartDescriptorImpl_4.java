		case BasicPackageImpl.PART_DESCRIPTOR__HANDLERS:
			return ((InternalEList<?>)getHandlers()).basicRemove(otherEnd, msgs);
		case BasicPackageImpl.PART_DESCRIPTOR__MENUS:
			return ((InternalEList<?>)getMenus()).basicRemove(otherEnd, msgs);
		case BasicPackageImpl.PART_DESCRIPTOR__TOOLBAR:
			return basicSetToolbar(null, msgs);
		case BasicPackageImpl.PART_DESCRIPTOR__PROPERTIES:
			return ((InternalEList<?>)((EMap.InternalMapView<String, String>)getProperties()).eMap()).basicRemove(otherEnd, msgs);
		case BasicPackageImpl.PART_DESCRIPTOR__TRIM_BARS:
			return ((InternalEList<?>)getTrimBars()).basicRemove(otherEnd, msgs);

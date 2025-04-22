		case BasicPackageImpl.PART__PROPERTIES:
			return ((InternalEList<?>) ((EMap.InternalMapView<String, String>) getProperties()).eMap())
					.basicRemove(otherEnd, msgs);
		case BasicPackageImpl.PART__HANDLERS:
			return ((InternalEList<?>) getHandlers()).basicRemove(otherEnd, msgs);
		case BasicPackageImpl.PART__MENUS:
			return ((InternalEList<?>) getMenus()).basicRemove(otherEnd, msgs);
		case BasicPackageImpl.PART__TOOLBAR:
			return basicSetToolbar(null, msgs);
		case BasicPackageImpl.PART__TRIM_BARS:
			return ((InternalEList<?>) getTrimBars()).basicRemove(otherEnd, msgs);

		case BasicPackageImpl.WINDOW__PROPERTIES:
			return ((InternalEList<?>)((EMap.InternalMapView<String, String>)getProperties()).eMap()).basicRemove(otherEnd, msgs);
		case BasicPackageImpl.WINDOW__HANDLERS:
			return ((InternalEList<?>)getHandlers()).basicRemove(otherEnd, msgs);
		case BasicPackageImpl.WINDOW__SNIPPETS:
			return ((InternalEList<?>)getSnippets()).basicRemove(otherEnd, msgs);
		case BasicPackageImpl.WINDOW__MAIN_MENU:
			return basicSetMainMenu(null, msgs);
		case BasicPackageImpl.WINDOW__WINDOWS:
			return ((InternalEList<?>)getWindows()).basicRemove(otherEnd, msgs);
		case BasicPackageImpl.WINDOW__SHARED_ELEMENTS:
			return ((InternalEList<?>)getSharedElements()).basicRemove(otherEnd, msgs);

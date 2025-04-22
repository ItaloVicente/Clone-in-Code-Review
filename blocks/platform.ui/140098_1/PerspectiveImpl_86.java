		case AdvancedPackageImpl.PERSPECTIVE__PROPERTIES:
			return ((InternalEList<?>) ((EMap.InternalMapView<String, String>) getProperties()).eMap())
					.basicRemove(otherEnd, msgs);
		case AdvancedPackageImpl.PERSPECTIVE__HANDLERS:
			return ((InternalEList<?>) getHandlers()).basicRemove(otherEnd, msgs);
		case AdvancedPackageImpl.PERSPECTIVE__WINDOWS:
			return ((InternalEList<?>) getWindows()).basicRemove(otherEnd, msgs);
		case AdvancedPackageImpl.PERSPECTIVE__TRIM_BARS:
			return ((InternalEList<?>) getTrimBars()).basicRemove(otherEnd, msgs);

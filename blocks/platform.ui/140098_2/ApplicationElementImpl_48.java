		case ApplicationPackageImpl.APPLICATION_ELEMENT__PERSISTED_STATE:
			return ((InternalEList<?>) ((EMap.InternalMapView<String, String>) getPersistedState()).eMap())
					.basicRemove(otherEnd, msgs);
		case ApplicationPackageImpl.APPLICATION_ELEMENT__TRANSIENT_DATA:
			return ((InternalEList<?>) ((EMap.InternalMapView<String, Object>) getTransientData()).eMap())
					.basicRemove(otherEnd, msgs);

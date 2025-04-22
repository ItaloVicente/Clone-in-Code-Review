			if (cleanPlaceholderRef) {
				ph.setRef(null);
			} else {
				EObject refEObj = (EObject) ph.getRef();
				EObject refEProxy = EcoreUtil.create(refEObj.eClass());
				((InternalEObject) refEProxy).eSetProxyURI(EcoreUtil.getURI(refEObj));
				ph.setRef((MUIElement) refEProxy);
			}

		if (obj == null || (obj.getClass() != getClass())) {
			return false;
		}
		AbstractElementSelector s = (AbstractElementSelector)obj;
		return (s.namespaceURI.equals(namespaceURI) && s.localName.equals(localName));
	}

	@Override

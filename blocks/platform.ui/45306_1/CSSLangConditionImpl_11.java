		if (obj == null || (obj.getClass() != getClass())) {
			return false;
		}
		CSSLangConditionImpl c = (CSSLangConditionImpl)obj;
		return c.lang.equals(lang);
	}

	@Override

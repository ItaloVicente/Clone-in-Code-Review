		if (rev != null)
			return rev.copy();
		if (ref != null)
			return ref;
		ref = getRef(revstr);
		if (ref != null)
			return ref;
		return resolveSimple(new String(revChars));

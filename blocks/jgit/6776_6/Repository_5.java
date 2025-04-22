		if (rev != null)
			return rev.copy();
		if (name != null)
			return name;
		name = revstr.substring(done);
		if (getRef(name) != null)
			return name;
		return resolveSimple(name);

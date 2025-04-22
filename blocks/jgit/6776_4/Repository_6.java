		if (rev != null)
			return rev.copy();
		if (ref != null)
			return ref;
		ref = getRef(revstr);
		if (ref != null)
			if (revstr.startsWith(Constants.R_REFS)
					&& ref.getName().equals(revstr))
				return ref.getLeaf().getObjectId();
			else
				return ref.getLeaf();
		else
			return resolveSimple(new String(revChars));

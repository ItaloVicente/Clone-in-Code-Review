		RevObject obj = parseAnyOrNull(id);
		if (obj != null) {
			advertiseAnyOnce(obj, ".have");
			if (obj instanceof RevTag)
				advertiseAnyOnce(((RevTag) obj).getObject(), ".have");
		}

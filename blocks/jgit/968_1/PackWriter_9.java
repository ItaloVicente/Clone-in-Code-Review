
		reader = db.newObjectReader();
		if (reader instanceof ObjectReuseAsIs)
			reuseSupport = ((ObjectReuseAsIs) reader);
		else
			reuseSupport = null;

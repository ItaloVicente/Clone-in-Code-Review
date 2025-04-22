
				(BuiltinCommandFactory) getStaticField(
						"org.eclipse.jgit.lfs.CleanFilter"
				(BuiltinCommandFactory) getStaticField(
						"org.eclipse.jgit.lfs.SmudgeFilter"
	}

	private Object getStaticField(String className
		Class cl;
		try {
			cl = Class.forName(className);
		if (cl == null)
			return null;
		Field f=cl.getField(fieldName);
		if (f == null)
			return null;
		return (f.get(null));
		} catch (IllegalArgumentException | IllegalAccessException
				| ClassNotFoundException | NoSuchFieldException
				| SecurityException e) {
			return null;
		}

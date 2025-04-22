	protected SymbolicRef findSymRef(String name
		String prefix = "symref="+name+":";
		for (String option : remoteCapablities) {
			if (option.startsWith(prefix)) {
				String target = option.substring(prefix.length());
				Ref r = refMap.get(target);
				if (r != null) {
					return new SymbolicRef(name
				}
			}
		}
		return null;
	}


	private Map<String
		Map<String
		for (String name : r.keySet())
			if (!refFilter.show(r.get(name)))
				r.remove(name);
		return r;
	}


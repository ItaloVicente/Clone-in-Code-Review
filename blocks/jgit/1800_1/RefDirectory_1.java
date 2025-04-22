	@Override
	public List<Ref> getAdditionalRefs() throws IOException {
		List<Ref> ret = new LinkedList<Ref>();
		for (String name : additionalRefsNames) {
			Ref r = getRef(name);
			if (r != null)
				ret.add(r);
		}
		return ret;
	}


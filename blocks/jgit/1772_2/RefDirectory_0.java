	@Override
	public List<Ref> getAdditionalRefs() {
		List<Ref> ret = new LinkedList<Ref>();
		for (String name : additionalRefsNames) {
			try {
				Ref r = getRef(name);
				if (r != null)
					ret.add(r);
			} catch (IOException e) {
			}
		}
		return ret;
	}


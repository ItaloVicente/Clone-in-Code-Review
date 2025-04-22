	protected Collection<String> stringify(Collection<?> c) {
		Collection<String> rv=new ArrayList<String>();
		for(Object o : c) {
			rv.add(String.valueOf(o));
		}
		return rv;
	}

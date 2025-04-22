	

	private Selector getSelector(String id, Class<?> clazz, List<String> tags){
		return new ElementMatcher(id, clazz, tags);
	}
	
	private Selector getSelector(String id){
		return getSelector(id, null, null);
	}
	
	private Selector getSelector(Class<?> clazz){
		return getSelector(null, clazz, null);
	}
	
	private Selector getSelector(){
		return getSelector(null, null, null);
	}
	
	private Selector getSelector(List<String> tags) {
		return getSelector(null, null, tags);
	}
	

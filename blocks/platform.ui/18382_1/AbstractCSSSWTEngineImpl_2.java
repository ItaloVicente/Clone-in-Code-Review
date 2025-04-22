	
	@SuppressWarnings("unchecked")
	@Override
	public Object convert(CSSValue value, Object toType, Object context) throws Exception {
		Object resource = super.convert(value, toType, context);

		if (resource instanceof VolatileResource) {	
			resource = processVolatileResource((VolatileResource<? extends Resource>) resource);
		}
		return resource;
	}	
	
	protected <T extends Resource> Resource processVolatileResource(VolatileResource<T> volatileResource) {
		T previousResource = volatileResource.getResource();
		T resource = volatileResource.isValid()? previousResource: getCurrentResource(volatileResource);	
		
		if (previousResource != resource) {			
			volatileResource.setResource(resource);
			addUnusedResource(previousResource);
		}
		return resource;
	}
	
	@SuppressWarnings("unchecked")
	protected <T extends Resource> T getCurrentResource(VolatileResource<T> volatileResource) {
		T result = null;
		if (volatileResource instanceof FontByDefinition) {
			result = (T) getCurrentFont((FontByDefinition) volatileResource);
		} else if (volatileResource instanceof ColorByDefinition) {
			result = (T) getCurrentColor((ColorByDefinition) volatileResource);
		} else {
			throw new IllegalArgumentException("CachedResource type is not supported: " + 
					volatileResource.getClass().getName());
		}
		if (result != null) {
			volatileResource.setValid(true);
		}
		return result;
	}
	
	protected Font getCurrentFont(FontByDefinition definition) {
		return CSSSWTFontHelper.getFont(definition);
	}
	
	protected Color getCurrentColor(ColorByDefinition definition) {
		return CSSSWTColorHelper.getSWTColor(definition);
	}
	
	private void addUnusedResource(Resource resource) {
		if (getResourcesRegistry() instanceof SWTResourcesRegistry) {
			((SWTResourcesRegistry) getResourcesRegistry()).addUnusedResource(resource);
		}
	}

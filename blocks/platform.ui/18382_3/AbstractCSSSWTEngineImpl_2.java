
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
		return volatileResource.isValid() ? volatileResource.getResource()
				: getCurrentResource(volatileResource);
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
		return result;
	}

	protected Font getCurrentFont(FontByDefinition definition) {
		return CSSSWTFontHelper.getFont(definition);
	}

	protected Color getCurrentColor(ColorByDefinition definition) {
		return CSSSWTColorHelper.getSWTColor(definition);
	}

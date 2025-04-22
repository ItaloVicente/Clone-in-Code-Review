	@Override
	public <T> T getAttribute(AttributeKey<T> key) {
		T value = super.getAttribute(key);
		if (value == null) {
			IoSession ioSession = getIoSession();
			if (ioSession != null) {
				Object obj = ioSession.getAttribute(AttributeRepository.class);
				if (obj instanceof AttributeRepository) {
					AttributeRepository sessionAttributes = (AttributeRepository) obj;
					value = sessionAttributes.resolveAttribute(key);
				}
			}
		}
		return value;
	}

	@Override
	public PropertyResolver getParentPropertyResolver() {
		IoSession ioSession = getIoSession();
		if (ioSession != null) {
			Object obj = ioSession.getAttribute(AttributeRepository.class);
			if (obj instanceof PropertyResolver) {
				return (PropertyResolver) obj;
			}
		}
		return super.getParentPropertyResolver();
	}

	public static class ChainingAttributes implements AttributeRepository {

		private final AttributeRepository delegate;

		private final AttributeRepository parent;

		public ChainingAttributes(AttributeRepository self
				AttributeRepository parent) {
			this.delegate = self;
			this.parent = parent;
		}

		@Override
		public int getAttributesCount() {
			return delegate.getAttributesCount();
		}

		@Override
		public <T> T getAttribute(AttributeKey<T> key) {
			return delegate.getAttribute(Objects.requireNonNull(key));
		}

		@Override
		public Collection<AttributeKey<?>> attributeKeys() {
			return delegate.attributeKeys();
		}

		@Override
		public <T> T resolveAttribute(AttributeKey<T> key) {
			T value = getAttribute(Objects.requireNonNull(key));
			if (value == null) {
				return parent.getAttribute(key);
			}
			return value;
		}
	}

	public static class SessionAttributes extends ChainingAttributes
			implements PropertyResolver {

		public static final AttributeKey<Map<String

		private final PropertyResolver parentProperties;

		public SessionAttributes(AttributeRepository self
				AttributeRepository parent
			super(self
			this.parentProperties = parentProperties;
		}

		@Override
		public PropertyResolver getParentPropertyResolver() {
			return parentProperties;
		}

		@Override
		public Map<String
			Map<String
			return props == null ? Collections.emptyMap() : props;
		}
	}

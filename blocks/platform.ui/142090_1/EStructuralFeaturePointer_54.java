		return new QName(null, getPropertyName());
	}

	public abstract String getPropertyName();

	public abstract void setPropertyName(String propertyName);

	public abstract int getPropertyCount();

	public abstract String[] getPropertyNames();

	protected abstract boolean isActualProperty();

	@Override

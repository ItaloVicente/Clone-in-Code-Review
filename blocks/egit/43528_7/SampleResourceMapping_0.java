
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SampleResourceMapping) {
			return file.equals(((SampleResourceMapping) obj).getModelObject())
					&& providerId.equals(((SampleResourceMapping) obj)
							.getModelProviderId());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(file, providerId);
	}

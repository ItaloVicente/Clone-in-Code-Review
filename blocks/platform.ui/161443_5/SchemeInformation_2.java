
	@Override
	public boolean equals(Object o) {
		if (o.getClass() != this.getClass()) {
			return false;
		}
		SchemeInformation other = (SchemeInformation) o;
		return Objects.equals(this.name, other.name) //
				&& Objects.equals(this.description, other.description) //
				&& this.handled == other.handled //
				&& Objects.equals(this.handlerInstanceLocation, other.handlerInstanceLocation);
	}

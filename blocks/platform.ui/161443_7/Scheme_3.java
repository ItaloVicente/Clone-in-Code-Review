	@Override
	public boolean equals(Object o) {
		if (o.getClass() != this.getClass()) {
			return false;
		}
		Scheme other = (Scheme) o;
		return Objects.equals(this.name, other.name) && Objects.equals(this.desription, other.desription);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, desription);
	}


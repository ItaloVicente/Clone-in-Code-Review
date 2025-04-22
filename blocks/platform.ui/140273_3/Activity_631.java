	boolean setDefined(boolean defined) {
		if (defined != this.defined) {
			this.defined = defined;
			hashCode = HASH_INITIAL;
			string = null;
			return true;
		}

		return false;
	}

	boolean setEnabled(boolean enabled) {
		if (enabled != this.enabled) {
			this.enabled = enabled;
			hashCode = HASH_INITIAL;
			string = null;
			return true;
		}

		return false;
	}

	boolean setName(String name) {
		if (!Util.equals(name, this.name)) {
			this.name = name;
			hashCode = HASH_INITIAL;
			string = null;
			return true;
		}

		return false;
	}

	void setExpression(Expression exp) {
		expression = exp;
	}

	boolean setDescription(String description) {
		if (!Util.equals(description, this.description)) {
			this.description = description;
			hashCode = HASH_INITIAL;
			string = null;
			return true;
		}

		return false;
	}

	@Override

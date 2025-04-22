	}

	boolean setCategoryActivityBindings(Set<ICategoryActivityBinding> categoryActivityBindings) {
		categoryActivityBindings = Util.safeCopy(categoryActivityBindings, ICategoryActivityBinding.class);

		if (!Objects.equals(categoryActivityBindings, this.categoryActivityBindings)) {
			this.categoryActivityBindings = categoryActivityBindings;
			this.categoryActivityBindingsAsArray = this.categoryActivityBindings
					.toArray(new ICategoryActivityBinding[this.categoryActivityBindings.size()]);
			hashCode = HASH_INITIAL;
			string = null;
			return true;
		}

		return false;
	}

	boolean setDefined(boolean defined) {
		if (defined != this.defined) {
			this.defined = defined;
			hashCode = HASH_INITIAL;
			string = null;
			return true;
		}

		return false;
	}

	boolean setName(String name) {
		if (!Objects.equals(name, this.name)) {
			this.name = name;
			hashCode = HASH_INITIAL;
			string = null;
			return true;
		}

		return false;
	}

	@Override

		switch (e.getType()) {
		case INSERT:
		case DELETE: {
			EditList r = new EditList();
			r.add(e);
			return r;
		}

		case REPLACE: {
			PatienceDiff<S> d = new PatienceDiff<S>(cmp
			d.diff(e);
			return d.edits;
		}

		case EMPTY:
			return new EditList();

		default:
			throw new IllegalStateException();
		}

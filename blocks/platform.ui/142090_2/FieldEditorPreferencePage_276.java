		if (fields != null) {
			Iterator<FieldEditor> e = fields.iterator();
			while (e.hasNext()) {
				FieldEditor pe = e.next();
				pe.loadDefault();
			}
		}
		checkState();
		super.performDefaults();
	}

	@Override

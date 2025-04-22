		if (fields != null) {
			Iterator<FieldEditor> e = fields.iterator();
			while (e.hasNext()) {
				FieldEditor pe = e.next();
				pe.store();
				pe.setPresentsDefaultValue(false);
			}
		}
		return true;
	}

	@Override

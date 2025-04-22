			dateTime.setDate(temporalAccessor.get(ChronoField.YEAR),
					temporalAccessor.get(ChronoField.MONTH_OF_YEAR) - MONTH_MAPPING_VALUE,
					temporalAccessor.get(ChronoField.DAY_OF_MONTH));
		}
	}

	private TemporalAccessor getTemporalAccessor(Object value) {
		TemporalAccessor temporalAccessor = null;

		if (value instanceof Date) {
			temporalAccessor = LocalDateTime.from(((Date) value).toInstant());
		} else if (value instanceof TemporalAccessor) {
			temporalAccessor = (TemporalAccessor) value;
		} else if (value instanceof Calendar) {
			temporalAccessor = LocalDateTime.from(((Calendar) value).toInstant());

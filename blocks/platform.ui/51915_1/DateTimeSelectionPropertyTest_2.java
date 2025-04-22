
	public void testLocalDateTime() {
		property.setValue(dateTime, LocalDateTime.now());
		assertTrue(true);
	}

	public void testLocalDate() {
		property.setValue(dateTime, LocalDate.now());
		assertTrue(true);
	}

	public void testLocalTime_UnsupportedTemporalTypeException() {
		try {
			property.setValue(dateTime, LocalTime.now());
			fail("Expected IllegalArgumentException");
		} catch (UnsupportedTemporalTypeException expected) {
		}
	}

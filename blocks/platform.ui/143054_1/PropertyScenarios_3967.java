		assertTrue(binding.getValidationStatus().getValue().isOK());
		enterText(text, "Invalid Value");
		assertEquals(noSpacesMessage, binding.getValidationStatus().getValue().getMessage());
		assertEquals("ValidValue", adventure.getName());
		text.setText("InvalidValueBecauseTooLong");
		assertEquals(max15CharactersMessage,
				binding.getValidationStatus().getValue().getMessage());
		assertEquals("ValidValue", adventure.getName());
		enterText(text, "anothervalid");
		assertTrue(binding.getValidationStatus().getValue().isOK());
		assertEquals("anothervalid", adventure.getName());
	}

	@Test

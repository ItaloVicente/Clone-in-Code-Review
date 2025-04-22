        assertTrue(((IStatus)binding.getValidationStatus().getValue()).isOK());
        enterText(text, "Invalid Value");
        assertEquals(noSpacesMessage, ((IStatus) binding.getValidationStatus().getValue()).getMessage());
        assertEquals("ValidValue", adventure.getName());
        text.setText("InvalidValueBecauseTooLong");
        assertEquals(max15CharactersMessage,
                ((IStatus) binding.getValidationStatus().getValue()).getMessage());
        assertEquals("ValidValue", adventure.getName());
        enterText(text, "anothervalid");
        assertTrue(((IStatus)binding.getValidationStatus().getValue()).isOK());
        assertEquals("anothervalid", adventure.getName());
    }

    @Test

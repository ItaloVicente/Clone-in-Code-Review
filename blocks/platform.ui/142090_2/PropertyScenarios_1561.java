				return ((String) fromObject).toUpperCase();
			}
		};

		getDbc().bindValue(SWTObservables.observeText(text, SWT.Modify),
				BeansObservables.observeValue(adventure, "name"),
				new UpdateValueStrategy().setConverter(converter2), new UpdateValueStrategy().setConverter(converter1));

		assertEquals("Uppercase", text.getText());
		enterText(text, "lowercase");
		assertEquals("LOWERCASE", adventure.getName());
	}

	@Test

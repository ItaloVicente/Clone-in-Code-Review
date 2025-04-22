				public void prepareAndCheckBeforeSerialization(
						IDialogSettings dialogSettingsToSerialize) {
					assertEquals(0,
							dialogSettingsToSerialize.getSections().length);
					assertEquals(null, dialogSettingsToSerialize
							.getSection(name));

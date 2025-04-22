			public void checkAfterDeserialization(
					IDialogSettings deserializedDialogSettings) {
				assertEquals(true, deserializedDialogSettings
						.getBoolean("true"));
				assertEquals(false, deserializedDialogSettings
						.getBoolean("false"));

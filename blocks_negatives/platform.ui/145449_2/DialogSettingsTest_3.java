					public void checkAfterDeserialization(
							IDialogSettings deserializedDialogSettings) {
						assertEquals(2, deserializedDialogSettings
								.getArray("someKey").length);
						assertEquals(value1, deserializedDialogSettings
								.getArray("someKey")[0]);
						assertEquals(value2, deserializedDialogSettings
								.getArray("someKey")[1]);
						assertEquals(0, deserializedDialogSettings
								.getArray("anotherKey1").length);

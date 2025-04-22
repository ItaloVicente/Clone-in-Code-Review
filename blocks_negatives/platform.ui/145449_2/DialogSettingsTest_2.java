						assertEquals(2, dialogSettingsToSerialize
								.getArray("someKey").length);
						assertEquals(value1, dialogSettingsToSerialize
								.getArray("someKey")[0]);
						assertEquals(value2, dialogSettingsToSerialize
								.getArray("someKey")[1]);
						dialogSettingsToSerialize.put("anotherKey1",
								new String[] {});

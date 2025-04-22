		writer.addScheme("adt", launcher);
		writer.addScheme("other", launcher);

		assertEntry(registryMock.setValues.get(0), "Software\\Classes\\adt", "URL Protocol", "");
		assertEntry(registryMock.setValues.get(1), "Software\\Classes\\adt", null, "URL:adt");
		assertEntry(registryMock.setValues.get(2), "Software\\Classes\\adt\\shell\\open\\command", "Executable",
				launcher);
		assertEntry(registryMock.setValues.get(3), "Software\\Classes\\adt\\shell\\open\\command", null,

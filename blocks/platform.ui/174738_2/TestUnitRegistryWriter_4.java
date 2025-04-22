		this.writer.addScheme("adt", launcher);
		this.writer.addScheme("other", launcher);

		this.assertEntry(this.registryMock.setValues.get(0), "Software\\Classes\\adt", "URL Protocol", "");
		this.assertEntry(this.registryMock.setValues.get(1), "Software\\Classes\\adt", null, "URL:adt");
		this.assertEntry(this.registryMock.setValues.get(2), "Software\\Classes\\adt\\shell\\open\\command",
				"Executable", launcher);
		this.assertEntry(this.registryMock.setValues.get(3), "Software\\Classes\\adt\\shell\\open\\command", null,

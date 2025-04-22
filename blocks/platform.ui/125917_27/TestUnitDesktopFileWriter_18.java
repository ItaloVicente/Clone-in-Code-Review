	@Test
	public void returnsMinimalDesktopFile() {
		List<String> actual = DesktopFileWriter.getMinimalDesktopFileContent("/home/myuser/Eclipse/eclipse/eclipse");
		List<String> expected = Arrays.asList(//
				"[Desktop Entry]", //
				"Exec=/home/myuser/Eclipse/eclipse/eclipse", //
				"NoDisplay=true", //
				"Type=Application");
		assertEquals(expected, actual);
	}

	@Test
	public void returnsMinimalDesktopFileWithSpaceEscapedInLocation() {
		List<String> actual = DesktopFileWriter
				.getMinimalDesktopFileContent("/home/myuser/Eclipse/eclipse (copy)/eclipse");
		List<String> expected = Arrays.asList(//
				"[Desktop Entry]", //
				"Exec=/home/myuser/Eclipse/eclipse\\ (copy)/eclipse", //
				"NoDisplay=true", //
				"Type=Application");
		assertEquals(expected, actual);
	}

	@Test
	public void returnsMinimalDesktopFileWithSpacesEscapedInLocation() {
		List<String> actual = DesktopFileWriter
				.getMinimalDesktopFileContent("/home/myuser/Eclipse/eclipse   (copy)/eclipse");
		List<String> expected = Arrays.asList(//
				"[Desktop Entry]", //
				"Exec=/home/myuser/Eclipse/eclipse\\ \\ \\ (copy)/eclipse", //
				"NoDisplay=true", //
				"Type=Application");
		assertEquals(expected, actual);
	}


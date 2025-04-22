			assertTrue(failMessage + System.lineSeparator() + "Line " + i + " '"
					+ actualLine + "' doesn't match expected pattern: "
					+ expectedPattern + System.lineSeparator() + "Expected: "
					+ Arrays.asList(expected) + ", actual: "
					+ Arrays.asList(actual),
					matches);

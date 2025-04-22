		for (String option : options) {
			assertArrayOfLinesEquals("Incorrect output for option: " + option,
					expectedOutput,
					runAndCaptureUsingInitRaw(MERGE_TOOL, option,
							TOOL_NAME));
		}

			String araxisErrorLine = "compare: unrecognized option `-wait' @ error/compare.c/CompareImageCommand/1123.";
			String[] expectedErrorOutput = { araxisErrorLine
					araxisErrorLine
			runAndCaptureUsingInitRaw(Arrays.asList(expectedErrorOutput)
					DIFF_TOOL
			fail("Expected exception to be thrown due to external tool exiting with an error");
		});

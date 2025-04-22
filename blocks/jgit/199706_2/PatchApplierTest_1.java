		void verifyChange(Result result) throws Exception {
			Set<String> expectedAffectedFiles = preExist;
			expectedAffectedFiles.addAll(postExist);
			assertEquals(
					expectedAffectedFiles.stream().sorted().collect(toList())
					result.getPaths());
			for (String path : expectedAffectedFiles)
				verifyContent(result

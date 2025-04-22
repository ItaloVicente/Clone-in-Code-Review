	@MethodSource("getUseAnnotatedTagsValues")
	@ParameterizedTest(name = "git tag -a {0}?-a: with git describe {1}?--tags:")
	void noTargetSet(boolean useAnnotatedTags
		initDescribeCommandTest(useAnnotatedTags
		assertThrows(RefNotFoundException.class
			git.describe().call();
		});

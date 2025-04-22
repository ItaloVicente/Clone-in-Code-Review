			final GitSynchronizeData data = new GitSynchronizeData(repo,
					Constants.HEAD, refName, true);
			final GitSynchronizeDataSet dataSet = new GitSynchronizeDataSet(
					data);

			final ResourceMapping[] mappings = ResourceUtil
					.getResourceMappings(file,
							ResourceMappingContext.LOCAL_CONTEXT);

			GitModelSynchronize.launch(dataSet, mappings);

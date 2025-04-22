			final GitSynchronizeData data = new GitSynchronizeData(repo, HEAD,
					refName, true);
			final GitSynchronizeDataSet dataSet = new GitSynchronizeDataSet(
					data);

			final ResourceMapping[] mappings = getResourceMappings(file,
					ResourceMappingContext.LOCAL_CONTEXT);

			GitModelSynchronize.launch(dataSet, mappings);

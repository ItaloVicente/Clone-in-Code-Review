				if (useWorkspace)
					oursCache.setVariant(resource, new GitLocalResourceVariant(
							resource));
				else
					oursCache.setVariant(resource,
							IndexResourceVariant.create(repository, entry));

				RepositoryMapping mapping = RepositoryMapping
						.getMapping(resource);
				if (mapping != null) {
					Repository newRep = mapping.getRepository();
					if (newRep != null && newRep != currentRepository) {
						if (monitor.isCanceled()) {
							return Status.CANCEL_STATUS;
						}
						reload(newRep);

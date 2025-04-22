			try {
				spec = PushOperationSpecification.create(repository, config,
						pushRefSpecs);
			} catch (NotSupportedException e) {
				throw new CoreException(
						Activator.createErrorStatus(e.getMessage(), e));
			} catch (IOException e) {
				throw new CoreException(
						Activator.createErrorStatus(e.getMessage(), e));

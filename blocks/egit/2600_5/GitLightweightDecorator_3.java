		if (resource.getType() == IResource.PROJECT) {
			try {
				decoratableResource = DecoratableResourceHelper
						.createTemporaryDecoratableResource(resource
								.getProject());
			} catch (IOException e) {
				handleException(
						resource,
						new CoreException(Activator.createErrorStatus(
								UIText.Decorator_exceptionMessage, e)));
				return;
			}
		}

		if (decoratableResource != null) {
			helper.decorate(decoration, decoratableResource);
		}


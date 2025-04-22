		if (resource.getType() == IResource.PROJECT) {
			try {
				decoratableResource = DecoratableResourceHelper
						.createTemporaryDecoratableResource(resource
								.getProject());
				helper.decorate(decoration, decoratableResource);
			} catch (IOException e) {
				handleException(
						resource,
						new CoreException(Activator.createErrorStatus(
								UIText.Decorator_exceptionMessage, e)));
				return;
			}
		}


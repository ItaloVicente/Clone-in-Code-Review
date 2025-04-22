			final IDecoratableResource decoratableResource = (IDecoratableResource) resource
					.getSessionProperty(DECORATABLE_RESOURCE_KEY);
			if (decoratableResource != null) {
				final DecorationHelper helper = new DecorationHelper(
						activator.getPreferenceStore());
				helper.decorate(decoration, decoratableResource);
				resource.setSessionProperty(DECORATABLE_RESOURCE_KEY, null);
			} else {
				GitDecoratorJob.getJobForRepository(
						mapping.getGitDirAbsolutePath().toString())
						.addDecorationRequest(element);
			}
		} catch (CoreException e) {

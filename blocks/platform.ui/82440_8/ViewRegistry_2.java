
			boolean useDependencyInjection = Boolean
					.parseBoolean(element.getAttribute(IWorkbenchConstants.TAG_USE_DEPENDENCY_INJECTION));
			if (useDependencyInjection) {
				descriptor.getTags().add(IWorkbenchConstants.TAG_USE_DEPENDENCY_INJECTION);
			}

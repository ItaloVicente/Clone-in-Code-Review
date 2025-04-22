			try {
				generator = (IMarkerResolutionGenerator) element.createExecutableExtension(ATT_CLASS);
			} catch (CoreException e) {
				Policy.handle(e);
				generator = GENERATOR_ERROR;
			}

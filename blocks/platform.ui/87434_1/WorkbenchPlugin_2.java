            BusyIndicator.showWhile(null, () -> {
			    try {
			        ret[0] = element
			                .createExecutableExtension(classAttribute);
			    } catch (CoreException e) {
			        exc[0] = e;
			    }
			});

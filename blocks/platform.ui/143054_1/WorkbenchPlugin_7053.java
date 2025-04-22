				return element.createExecutableExtension(classAttribute);
			}
			final Object[] ret = new Object[1];
			final CoreException[] exc = new CoreException[1];
			BusyIndicator.showWhile(null, () -> {
				try {
					ret[0] = element.createExecutableExtension(classAttribute);
				} catch (CoreException e) {
					exc[0] = e;
				}

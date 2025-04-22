		} else {
            final Object[] ret = new Object[1];
            final CoreException[] exc = new CoreException[1];
            BusyIndicator.showWhile(null, new Runnable() {
                @Override
				public void run() {
                    try {
                        ret[0] = element
                                .createExecutableExtension(classAttribute);
                    } catch (CoreException e) {
                        exc[0] = e;
                    }
                }
            });
            if (exc[0] != null) {
				throw exc[0];
			} else {
				return ret[0];

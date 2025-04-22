				if (rsrc.getType() == IResource.FILE) {
					Object sessionProperty = null;
					try {
						sessionProperty = rsrc
								.getSessionProperty(LENGTH_RESOURCE_KEY);
					} catch (CoreException e) {
					}
					if (sessionProperty != null) {
						try {
							length = ((Long) sessionProperty).longValue();
						} catch (ClassCastException cce) {
							try {
								rsrc.setSessionProperty(LENGTH_RESOURCE_KEY,
										null);
							} catch (CoreException ce) {
								exceptions.handleException(ce);
							}
						}
					} else {
						length = asFile().length();
						try {
							rsrc.setSessionProperty(LENGTH_RESOURCE_KEY,
									new Long(length));
						} catch (CoreException e) {
						}
					}
				} else {

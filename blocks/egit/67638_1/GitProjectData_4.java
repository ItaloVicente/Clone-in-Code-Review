			for (RepositoryMapping m : d.mappings.values()) {
				IContainer c = m.getContainer();
				if (c != null && c.isAccessible()) {
					try {
						c.setSessionProperty(MAPPING_KEY, null);
					} catch (CoreException e) {
						Activator.logWarning(MessageFormat.format(
								CoreText.GitProjectData_failedToUnmapRepoMapping,
								c.getFullPath()), e);
					}
				}
			}
			trace("uncacheDataFor(" //$NON-NLS-1$
					+ p.getName() + ") done"); //$NON-NLS-1$

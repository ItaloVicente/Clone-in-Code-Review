			RepositoryMapping mapping = RepositoryMapping.getMapping(
					path);
			if (mapping == null) {
				String message = NLS.bind(
						CoreText.IgnoreOperation_parentOutsideRepo,
						path.toOSString(), null);
				IStatus status = Activator.error(message, null);
				throw new CoreException(status);
			}
			Repository repository = mapping.getRepository();

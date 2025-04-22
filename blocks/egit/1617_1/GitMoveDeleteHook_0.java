		final RepositoryMapping srcm = RepositoryMapping.getMapping(source);
		if (srcm == null)
			return false;
		File newLocation = new File(description.getLocationURI().getPath());
		if(newLocation.getAbsolutePath().contains(srcm.getRepository().getWorkTree().getAbsolutePath())) {
			final String sPath = srcm.getRepoRelativePath(source);
			final String dPath = new Path(newLocation.getAbsolutePath().substring(
					srcm.getRepository().getWorkTree().getAbsolutePath().length() + 1) + "/").toPortableString(); //$NON-NLS-1$
			try {
				if (!moveIndexContent(dPath, srcm, sPath))
					tree.failed(new Status(IStatus.ERROR, Activator.getPluginId(),
							0, CoreText.MoveDeleteHook_operationError, null));
				tree.standardMoveProject(source, description, updateFlags, monitor);
			} catch (IOException e) {
				tree.failed(new Status(IStatus.ERROR, Activator.getPluginId(), 0,
						CoreText.MoveDeleteHook_operationError, e));
			}
			return true;
		}


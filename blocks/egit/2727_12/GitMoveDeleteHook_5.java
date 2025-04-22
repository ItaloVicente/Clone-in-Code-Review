			final String absoluteWorkTreePath = srcm.getRepository().getWorkTree().getAbsolutePath();
			final String newLocationAbsolutePath = newLocationFile.getAbsolutePath();
			final String dPath;
			if (newLocationAbsolutePath.equals(absoluteWorkTreePath))
				dPath = ""; //$NON-NLS-1$
			else
				dPath = new Path(
						newLocationAbsolutePath.substring(absoluteWorkTreePath
								.length() + 1) + "/").toPortableString(); //$NON-NLS-1$

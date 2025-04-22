		else if (revision instanceof IAdaptable) {
			IResourceVariant variant = Adapters.adapt(((IAdaptable) revision),
					IResourceVariant.class);

			if (variant instanceof GitRemoteResource)
				return ((GitRemoteResource) variant).getPath();

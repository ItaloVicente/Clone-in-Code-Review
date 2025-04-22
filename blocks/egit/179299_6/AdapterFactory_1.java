		} else if (adapterType == GitInfo.class) {
			if (adaptableObject instanceof IResource
					&& ((IResource) adaptableObject)
							.getType() != IResource.ROOT) {
				return adapterType
						.cast(new GitAccessor((IResource) adaptableObject));
			}

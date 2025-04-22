			}

			Object r = getAdaptedContributorResource(object);
			if (r != null) {
				return Platform.getAdapterManager().getAdapter(r, resourceMappingClass);
			}

			return null;
		}
		return Platform.getAdapterManager().getAdapter(object, resourceMappingClass);
	}

	public static IStructuredSelection adaptSelection(IStructuredSelection selection, String objectClass) {

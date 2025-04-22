			if (element instanceof PathNodeAdapter)
				return DELETEDCATEGORY;

			return UNKNOWNCATEGORY;
		}

		private Object getAdapter(Object sourceObject, Class adapterType) {
			Assert.isNotNull(adapterType);
			if (sourceObject == null)
				return null;

			if (adapterType.isInstance(sourceObject))
				return sourceObject;

			if (sourceObject instanceof IAdaptable) {
				IAdaptable adaptable = (IAdaptable) sourceObject;

				Object result = adaptable.getAdapter(adapterType);
				if (result != null) {
					Assert.isTrue(adapterType.isInstance(result));
					return result;

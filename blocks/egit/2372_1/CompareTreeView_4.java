
			if (!(sourceObject instanceof PlatformObject)) {
				Object result = Platform.getAdapterManager().getAdapter(
						sourceObject, adapterType);
				if (result != null) {
					return result;

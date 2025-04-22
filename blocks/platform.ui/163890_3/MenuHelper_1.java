	private static String getUrlSupplier(Class<? extends ImageDescriptor> idc, ImageDescriptor imageDescriptor) {
		try {
			if (urlSupplierField == null) {
				urlSupplierField = idc.getDeclaredField("supplier"); //$NON-NLS-1$
				urlSupplierField.setAccessible(true);
			}
			Object value = urlSupplierField.get(imageDescriptor);
			if (value != null && value instanceof Supplier) {
				@SuppressWarnings("unchecked")
				Supplier<URL> supplier = (Supplier<URL>) value;
				URL url = supplier.get();
				return url == null ? null : url.toString();
			}
		} catch (SecurityException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
			WorkbenchPlugin.log(e);
		}
		return null;
	}


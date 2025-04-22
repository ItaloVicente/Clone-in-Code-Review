
	private String getLabel(CSSValue value) {
		URL resourceBundleURL = getResourceBundleURL(value);
		if (resourceBundleURL != null) {
			String messageId = getMessageId(resourceBundleURL);
			if (messageId != null) {
				ResourceBundle resourceBundle = getResourceBundle(resourceBundleURL);
				String message = resourceBundle != null ? resourceBundle.getString(messageId) : null;
				if (message != null) {
					return resourceBundle.getString(messageId);
				}
			}
		}
		return value.getCssText();
	}

	private String getMessageId(URL resourceBundleURL) {
		String query = resourceBundleURL.getQuery();
		if (query != null) {
			int indexOfMessageParam = query.indexOf(MESSAGE_QUERY_PARAM);
			if (indexOfMessageParam != -1) {
				return query.substring(indexOfMessageParam + MESSAGE_QUERY_PARAM.length() + 1);
			}
		}
		return null;
	}

	private Bundle getBundle(URI uri) throws BundleException {
		Bundle bundle = CSSActivator.getDefault().getBundleForName(uri.lastSegment());
		if (bundle != null && (bundle.getState() & Bundle.ACTIVE) == 0) {
			bundle.start(); // Bundle is lazy init
		}
		return bundle;
	}

	private ResourceBundle getResourceBundle(URL resourceBundleURL) {
		ResourceBundle resourceBundle = null;
		try {
			URI uri = URI.createURI(resourceBundleURL.getPath());
			if (uri != null) {
				resourceBundle = getResourceBundle(getBundle(uri));
			}
		} catch (Exception exc) {
		}
		return resourceBundle;
	}

	private ResourceBundle getResourceBundle(Bundle bundle) {
		if (bundle == null) {
			return null;
		}
		ResourceBundle resourceBundle = bundleToResourceBundles.get(bundle.getBundleId());
		if (resourceBundle != null) {
			return resourceBundle;
		}

		BundleLocalization localization = getBundleLocalization(bundle);
		if (localization != null) {
			resourceBundle = localization.getLocalization(bundle, null);
		}
		if (resourceBundle != null) {
			bundleToResourceBundles.put(bundle.getBundleId(), resourceBundle);
		}
		return resourceBundle;
	}

	private BundleLocalization getBundleLocalization(Bundle bundle) {
		ServiceReference<BundleLocalization> ref = bundle.getBundleContext().getServiceReference(
				BundleLocalization.class);
		return bundle.getBundleContext().getService(ref);
	}

	private URL getResourceBundleURL(CSSValue value) {
		URL url = null;
		if (hasResourceBundleUrl(value)) {
			try {
				url = new URL(((CSSPrimitiveValue) value).getStringValue());
			} catch (MalformedURLException exc) {
			}
		}
		return url;
	}

	private boolean hasResourceBundleUrl(CSSValue value) {
		return value.getCssValueType() == CSSValue.CSS_PRIMITIVE_VALUE
				&& ((CSSPrimitiveValue) value).getPrimitiveType() == CSSPrimitiveValue.CSS_URI;
	}

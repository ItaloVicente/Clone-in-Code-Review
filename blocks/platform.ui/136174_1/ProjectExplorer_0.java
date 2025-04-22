		Object data = getCommonViewer().getData(NavigatorPlugin.RESOURCE_REGEXP_FILTER_DATA);
		if (data instanceof Collection) {
			Collection<?> dataAsFilters = (Collection<?>) data;
			for (Object object : dataAsFilters) {
				if (!(object instanceof UserFilter)) {
					continue;
				}
				UserFilter filter = (UserFilter) object;

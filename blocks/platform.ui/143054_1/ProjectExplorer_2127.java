	private List<UserFilter> userFilters;
	private EmptyWorkspaceHelper emptyWorkspaceHelper;

	@Override
	public void init(IViewSite site, IMemento memento) throws PartInitException {
		super.init(site, memento);
		userFilters = new ArrayList<UserFilter>();
		if (memento != null) {
			IMemento[] filters = memento.getChildren(MEMENTO_REGEXP_FILTER_ELEMENT);
			for (IMemento filterMemento : filters) {
				String regexp = filterMemento.getString(MEMENTO_REGEXP_FILTER_REGEXP_ATTRIBUTE);
				Boolean enabled = filterMemento.getBoolean(MEMENTO_REGEXP_FILTER_ENABLED_ATTRIBUTE);
				userFilters.add(new UserFilter(regexp, enabled));
			}
		}
	}

	@Override
	public void saveState(IMemento aMemento) {
		Object data = getCommonViewer().getData(NavigatorPlugin.RESOURCE_REGEXP_FILTER_DATA);
		if (data instanceof Collection) {
			Collection<?> dataAsFilters = (Collection<?>) data;
			for (Object object : dataAsFilters) {
				if (!(object instanceof UserFilter)) {
					continue;
				}
				UserFilter filter = (UserFilter) object;
				IMemento memento = aMemento.createChild(MEMENTO_REGEXP_FILTER_ELEMENT);
				memento.putString(MEMENTO_REGEXP_FILTER_REGEXP_ATTRIBUTE, filter.getRegexp());
				memento.putBoolean(MEMENTO_REGEXP_FILTER_ENABLED_ATTRIBUTE, filter.isEnabled());
			}
		}
		super.saveState(aMemento);
	}


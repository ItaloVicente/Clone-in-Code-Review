	private List<UserFilter> userFilters;


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
		Collection<UserFilter> dataAsFilters = (Collection<UserFilter>)getCommonViewer().getData(NavigatorPlugin.RESOURCE_REGEXP_FILTER_DATA);
		if (dataAsFilters != null) {
			for (UserFilter filter : dataAsFilters) {
				IMemento memento = aMemento.createChild(MEMENTO_REGEXP_FILTER_ELEMENT);
				memento.putString(MEMENTO_REGEXP_FILTER_REGEXP_ATTRIBUTE, filter.getRegexp());
				memento.putBoolean(MEMENTO_REGEXP_FILTER_ENABLED_ATTRIBUTE, filter.isEnabled());
			}
		}
		super.saveState(aMemento);
	}



			menu.appendToGroup(FILTER_ACTION_GROUP,
					new GroupMarker(FILTER_ACTION_GROUP_FILTERS_START));

			menu.appendToGroup(FILTER_ACTION_GROUP_FILTERS_START,
					new Separator(FILTER_ACTION_GROUP_FILTERS_END));


			for (Iterator iter = filterShortcutActions.iterator(); iter.hasNext();) {
				IAction action = (IAction) iter.next();
				menu.appendToGroup(FILTER_ACTION_GROUP_FILTERS_START, action);
			}


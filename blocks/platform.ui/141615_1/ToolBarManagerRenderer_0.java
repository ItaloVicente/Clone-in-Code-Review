			for (Object o : UIEvents.asIterable(event, UIEvents.EventTags.NEW_VALUE)) {
				MToolBarElement added = (MToolBarElement) o;
				modelProcessSwitch(parentManager, added);
			}
		} else if (UIEvents.isREMOVE(event)) {
			for (Object o : UIEvents.asIterable(event, UIEvents.EventTags.OLD_VALUE)) {
				MToolBarElement removed = (MToolBarElement) o;
				disposeToolBarElement(parentManager, removed);
			}
		} else if (UIEvents.isMOVE(event)) {
			Object oldPos = event.getProperty(UIEvents.EventTags.OLD_VALUE);
			Object newPos = event.getProperty(UIEvents.EventTags.POSITION);

			IContributionItem ici = parentManager.getItems()[(Integer) oldPos];
			parentManager.remove(ici);
			parentManager.insert((Integer) newPos, ici);
		}

		parentManager.update(true);
		ToolBar toolbar = getToolbarFrom(toolbarModel.getWidget());
		if (toolbar != null) {
			toolbar.requestLayout();

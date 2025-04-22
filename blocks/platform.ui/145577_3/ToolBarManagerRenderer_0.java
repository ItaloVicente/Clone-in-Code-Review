			for (Object o : UIEvents.asIterable(event, UIEvents.EventTags.NEW_VALUE)) {
				MToolBarElement added = (MToolBarElement) o;
				modelProcessSwitch(parentManager, added);
			}
			updateWidget(parentManager);
		} else if (UIEvents.isREMOVE(event)) {
			for (Object o : UIEvents.asIterable(event, UIEvents.EventTags.OLD_VALUE)) {
				MToolBarElement removed = (MToolBarElement) o;
				removed.setRenderer(null);
				removeElement(parentManager, removed);
			}
			updateWidget(parentManager);
		} else if (UIEvents.isMOVE(event)) {
			MToolBarElement moved = (MToolBarElement) event.getProperty(UIEvents.EventTags.NEW_VALUE);
			Integer newPos = (Integer) event.getProperty(UIEvents.EventTags.POSITION);

			IContributionItem ici = getContribution(moved);
			parentManager.remove(ici);
			parentManager.insert(newPos, ici);

			updateWidget(parentManager);

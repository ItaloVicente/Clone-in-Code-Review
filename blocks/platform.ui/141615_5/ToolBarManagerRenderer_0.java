			for (Object o : UIEvents.asIterable(event, UIEvents.EventTags.NEW_VALUE)) {
				MToolBarElement added = (MToolBarElement) o;
				modelProcessSwitch(parentManager, added);
			}

			updateWidget(parentManager);
		} else if (UIEvents.isREMOVE(event)) {
			for (Object o : UIEvents.asIterable(event, UIEvents.EventTags.OLD_VALUE)) {
				MToolBarElement removed = (MToolBarElement) o;
				disposeToolBarElement(parentManager, removed);
			}

			updateWidget(parentManager);
		} else if (UIEvents.isMOVE(event)) {
			Object oldPos = event.getProperty(UIEvents.EventTags.OLD_VALUE);
			Object newPos = event.getProperty(UIEvents.EventTags.POSITION);

			IContributionItem ici = parentManager.getItems()[(Integer) oldPos];
			parentManager.remove(ici);
			parentManager.insert((Integer) newPos, ici);

			updateWidget(parentManager);

			MMenuItem itemModel = (MMenuItem) event
					.getProperty(UIEvents.EventTags.ELEMENT);
			IContributionItem ici = getContribution(itemModel);
			if (ici != null) {
				ici.update();
			}

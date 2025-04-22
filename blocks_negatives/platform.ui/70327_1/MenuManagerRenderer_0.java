			String attName = (String) event
					.getProperty(UIEvents.EventTags.ATTNAME);
			if (UIEvents.UILabel.LABEL.equals(attName)
					|| UIEvents.UILabel.LOCALIZED_LABEL.equals(attName)) {
				ici.update();
			} else if (UIEvents.UILabel.ICONURI.equals(attName)) {
				ici.update();
			} else if (UIEvents.UILabel.TOOLTIP.equals(attName) || UIEvents.UILabel.LOCALIZED_TOOLTIP.equals(attName)) {
				ici.update();
			}

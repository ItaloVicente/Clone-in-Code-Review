			String attName = (String) event
					.getProperty(UIEvents.EventTags.ATTNAME);
			if (UIEvents.UILabel.LABEL.equals(attName)
					|| UIEvents.UILabel.LOCALIZED_LABEL.equals(attName)) {
				areaItem.setText(areaModel.getLocalizedLabel());
			} else if (UIEvents.UILabel.ICONURI.equals(attName)) {
				areaItem.setImage(getImage(areaModel));
			} else if (UIEvents.UILabel.TOOLTIP.equals(attName)
					|| UIEvents.UILabel.LOCALIZED_TOOLTIP.equals(attName)) {
				areaItem.setToolTipText(areaModel.getLocalizedTooltip());
			}

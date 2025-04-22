			String attName = (String) event.getProperty(UIEvents.EventTags.ATTNAME);
			MMenu model = (MMenu) event.getProperty(UIEvents.EventTags.ELEMENT);
			MenuManager manager = getManager(model);
			if ((manager == null))
				return;
			if (UIEvents.UILabel.LABEL.equals(attName) || UIEvents.UILabel.LOCALIZED_LABEL.equals(attName)) {
				manager.setMenuText(getText(model));
				manager.update(IAction.TEXT);
			}
			if (UIEvents.UILabel.ICONURI.equals(attName)) {
				manager.setImageDescriptor(getImageDescriptor(model));
				manager.update(IAction.IMAGE);
			}

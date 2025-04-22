		if (UIEvents.UILabel.LABEL.equals(attName) || UIEvents.UILabel.LOCALIZED_LABEL.equals(attName)) {
			manager.setMenuText(getText(model));
			manager.update(IAction.TEXT);
		}
		if (UIEvents.UILabel.ICONURI.equals(attName)) {
			manager.setImageDescriptor(getImageDescriptor(model));
			manager.update(IAction.IMAGE);
		}
	}

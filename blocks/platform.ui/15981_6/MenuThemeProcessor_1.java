
		for (ITheme theme : themes) {
			MParameter parameter = service.createModelElement(MParameter.class);
			parameter.setName("contacts.commands.switchtheme.themeid"); //$NON-NLS-1$
			parameter.setValue(theme.getId());
			String iconURI = Util.getCSSUri(theme.getId(), registery);
			if (iconURI != null) {
				iconURI = iconURI.replace(".css", ".png");
			}
			processTheme(theme.getLabel(), switchThemeCommand, parameter,
					iconURI, service);
		}
		menu.getChildren().add(themesMenu);

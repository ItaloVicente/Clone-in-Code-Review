		List<ITheme> themes = engine.getThemes();
		if (themes.size() > 0) {

			MCommand switchThemeCommand = null;
			for (MCommand cmd : app.getCommands()) {
				if ("contacts.switchTheme".equals(cmd.getElementId())) { //$NON-NLS-1$
					switchThemeCommand = cmd;
					break;
				}
			}

			if (switchThemeCommand != null) {

				themesMenu = MMenuFactory.INSTANCE.createMenu();
				themesMenu.setLabel("%switchThemeMenu"); //$NON-NLS-1$
				themesMenu.setContributorURI(BUNDLE_ID);

				for (ITheme theme : themes) {
					MParameter parameter = service
							.createModelElement(MParameter.class);
					parameter.setName("contacts.commands.switchtheme.themeid"); //$NON-NLS-1$
					parameter.setValue(theme.getId());
					String iconURI = Util.getCSSUri(theme.getId(), registery);
					if (iconURI != null) {
						iconURI = iconURI.replace(".css", ".png");
					}
					processTheme(theme.getLabel(), switchThemeCommand,
							parameter, iconURI, service);
				}
				menu.getChildren().add(themesMenu);
			}
		}

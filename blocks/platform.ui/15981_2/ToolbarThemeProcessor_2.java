			if (switchThemeCommand != null) {

				toolbar.getChildren().add(
						MMenuFactory.INSTANCE.createToolBarSeparator());

				for (ITheme theme : themes) {
					MParameter parameter = MCommandsFactory.INSTANCE
							.createParameter();
					parameter.setName("contacts.commands.switchtheme.themeid"); //$NON-NLS-1$
					parameter.setValue(theme.getId());
					String iconURI = getCSSUri(theme.getId(), registery);
					if (iconURI != null) {
						iconURI = iconURI.replace(".css", ".png");
					}
					processTheme(theme.getLabel(), switchThemeCommand,
							parameter, iconURI, service);
				}

			}
		}

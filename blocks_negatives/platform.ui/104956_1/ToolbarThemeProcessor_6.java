			toolbar.getChildren().add(
					service.createModelElement(MToolBarSeparator.class));

			for (ITheme theme : themes) {
				if (!theme.getId().startsWith("org.eclipse.e4.demo.contacts.")) {
					return;
				}
				MParameter parameter = service
						.createModelElement(MParameter.class);
				parameter.setValue(theme.getId());
				String iconURI = ThemeHelper
						.getCSSUri(theme.getId(), registery);
				if (iconURI != null) {
					iconURI = iconURI.replace(".css", ".png");
				}
				processTheme(theme.getLabel(), switchThemeCommand, parameter,
						iconURI, service);

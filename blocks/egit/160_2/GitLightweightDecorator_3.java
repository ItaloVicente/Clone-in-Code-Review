			decorateFontAndColour(decoration, resource);
		}

		private void decorateFontAndColour(IDecoration decoration,
				IDecoratableResource resource) {
			ITheme current = PlatformUI.getWorkbench().getThemeManager().getCurrentTheme();
			if (resource.isIgnored()) {
				return;
			}
			if (!resource.isTracked()
					|| resource.isDirty()
					|| resource.staged() != Staged.NOT_STAGED) {
				Color bc = current.getColorRegistry().get(UIPreferences.THEME_UncommittedChangeBackgroundColor);
				Color fc = current.getColorRegistry().get(UIPreferences.THEME_UncommittedChangeForegroundColor);
				Font f = current.getFontRegistry().get(UIPreferences.THEME_UncommittedChangeFont);

				decoration.setBackgroundColor(bc);
				decoration.setForegroundColor(fc);
				decoration.setFont(f);
			}

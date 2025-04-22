	private static class ThemePreferenceStore extends PreferenceStore {


		private static final String ADD_ANNOTATION_COLOR_PREFERENCE = "org.eclipse.egit.ui.commitEditor.diffAddedColor"; //$NON-NLS-1$

		private static final String REMOVE_ANNOTATION_COLOR_PREFERENCE = "org.eclipse.egit.ui.commitEditor.diffRemovedColor"; //$NON-NLS-1$

		private final IPropertyChangeListener listener = event -> {
			String property = event.getProperty();
			if (IThemeManager.CHANGE_CURRENT_THEME.equals(property)) {
				setColorRegistry();
				initColors();
			} else if (THEME_DiffAddBackgroundColor.equals(property)
					|| THEME_DiffRemoveBackgroundColor.equals(property)) {
				initColors();
			}
		};

		private ColorRegistry currentColors;

		public ThemePreferenceStore() {
			super();
			setColorRegistry();
			initColors();
			PlatformUI.getWorkbench().getThemeManager()
					.addPropertyChangeListener(listener);
		}

		private void setColorRegistry() {
			if (currentColors != null) {
				currentColors.removeListener(listener);
			}
			currentColors = PlatformUI.getWorkbench().getThemeManager()
					.getCurrentTheme().getColorRegistry();
			currentColors.addListener(listener);
		}

		private void initColors() {
			RGB rgb = adjust(currentColors.getRGB(THEME_DiffAddBackgroundColor),
					4.0);
			setValue(ADD_ANNOTATION_COLOR_PREFERENCE,
					String.format("%d,%d,%d", Integer.valueOf(rgb.red), //$NON-NLS-1$
							Integer.valueOf(rgb.green),
							Integer.valueOf(rgb.blue)));
			rgb = adjust(currentColors.getRGB(THEME_DiffRemoveBackgroundColor),
					4.0);
			setValue(REMOVE_ANNOTATION_COLOR_PREFERENCE,
					String.format("%d,%d,%d", Integer.valueOf(rgb.red), //$NON-NLS-1$
							Integer.valueOf(rgb.green),
							Integer.valueOf(rgb.blue)));
		}

		private RGB adjust(RGB rgb, double saturation) {
			float[] hsb = rgb.getHSB();
			return new RGB(hsb[0], (float) Math.min(hsb[1] * saturation, 1.0),
					hsb[2] < 0.5 ? hsb[2] * 2 : hsb[2]);
		}

		public void dispose() {
			PlatformUI.getWorkbench().getThemeManager()
					.removePropertyChangeListener(listener);
			if (currentColors != null) {
				currentColors.removeListener(listener);
				currentColors = null;
			}
		}
	}

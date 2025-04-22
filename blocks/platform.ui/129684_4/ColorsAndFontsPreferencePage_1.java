			private String getText(Object element) {
				String text = EMPTY;
				if (element instanceof ICategorizedThemeElementDefinition) {
					text += ((ICategorizedThemeElementDefinition) element).getDescription();
				}
				if (element instanceof FontDefinition) {
					Font font = fontRegistry.get(((FontDefinition) element).getId());
					if (font != null) {
						for (FontData data : font.getFontData()) {
							text += SPACE;
							text += data.toString() + SPACE;
							text += data.getStyle() == SWT.NORMAL ? RESOURCE_BUNDLE.getString("normalFont") : EMPTY; //$NON-NLS-1$
							text += data.getStyle() == SWT.BOLD ? RESOURCE_BUNDLE.getString("boldFont") : EMPTY; //$NON-NLS-1$
							text += data.getStyle() == SWT.ITALIC ? RESOURCE_BUNDLE.getString("italicFont") : EMPTY; //$NON-NLS-1$
							break;
						}
					}
				} else if (element instanceof ColorDefinition) {
					Color color = colorRegistry.get(((ColorDefinition) element).getId());
					text += SPACE + RESOURCE_BUNDLE.getString("red") + COLON + color.getRed(); //$NON-NLS-1$
					text += SPACE + RESOURCE_BUNDLE.getString("green") + COLON + color.getGreen(); //$NON-NLS-1$
					text += SPACE + RESOURCE_BUNDLE.getString("blue") + COLON + color.getBlue(); //$NON-NLS-1$
				}
				return text;

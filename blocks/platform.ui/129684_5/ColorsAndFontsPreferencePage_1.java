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
				}
				return text;

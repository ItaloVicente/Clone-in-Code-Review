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
							text += data.getName() + SPACE;
							text += data.getHeight() + SPACE;
							text += data.getStyle() == SWT.NORMAL ? RESOURCE_BUNDLE.getString("normalFont") + SPACE //$NON-NLS-1$
									: EMPTY;
							text += (data.getStyle() & SWT.BOLD) == SWT.BOLD
									? RESOURCE_BUNDLE.getString("boldFont") + SPACE //$NON-NLS-1$
									: EMPTY;
							text += (data.getStyle() & SWT.ITALIC) == SWT.ITALIC
									? RESOURCE_BUNDLE.getString("italicFont") + SPACE //$NON-NLS-1$
									: EMPTY;
							break;
						}
					}
				}
				return text;

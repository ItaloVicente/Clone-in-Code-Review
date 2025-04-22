			private String getText(Object element) {
				String text = ""; //$NON-NLS-1$
				if (element instanceof ICategorizedThemeElementDefinition) {
					text += ((ICategorizedThemeElementDefinition) element).getDescription();
				}
				if (element instanceof FontDefinition) {
					Font font = fontRegistry.get(((FontDefinition) element).getId());
					if (font != null) {
						for (FontData data : font.getFontData()) {
							text += " "; //$NON-NLS-1$
							text += data.toString();
							text += data.getStyle() == SWT.BOLD ? " bold" : ""; //$NON-NLS-1$//$NON-NLS-2$
							text += data.getStyle() == SWT.ITALIC ? " italic" : ""; //$NON-NLS-1$//$NON-NLS-2$
							text += data.getStyle() == SWT.NORMAL ? " normal" : ""; //$NON-NLS-1$//$NON-NLS-2$
							break;
						}
					}
				}
				else if (element instanceof ColorDefinition) {
					Color color = colorRegistry.get(((ColorDefinition) element).getId());
					text += " red:" + color.getRed(); //$NON-NLS-1$
					text += " green:" + color.getGreen(); //$NON-NLS-1$
					text += " blue:" + color.getBlue(); //$NON-NLS-1$
				}
				System.out.println(text); // FIXME to be removed
				return text;

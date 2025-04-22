			StyledString str = new StyledString(res.getName().trim());

			String searchFieldString = ((Text) getPatternControl()).getText();
			searchFieldString = searchFieldString.replaceAll("\\*", ""); //$NON-NLS-1$//$NON-NLS-2$
			searchFieldString = searchFieldString.replaceAll("\\?", ""); //$NON-NLS-1$//$NON-NLS-2$
			Pattern p = Pattern.compile(searchFieldString, Pattern.CASE_INSENSITIVE); // $NON-NLS-1$
			Matcher m = p.matcher(str);
			if (m.find()) {
				str.setStyle(m.start(), m.end() - m.start(), new Styler() {
					@Override
					public void applyStyles(TextStyle textStyle) {
						textStyle.font = JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT);
					}
				});
			}

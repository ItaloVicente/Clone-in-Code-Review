				File file = element;

				Styler style = file.isDirectory() ? fBoldStyler : null;
				StyledString styledString = new StyledString(file.getName(),
						style);
				String decoration = MessageFormat
						.format(" ({0} bytes)", new Object[] { new Long(file.length()) }); //$NON-NLS-1$

			String name = commit.getShortMessage();

			name = name.trim();
			try {
				name = URLEncoder.encode(name, "UTF-8"); //$NON-NLS-1$
			} catch (UnsupportedEncodingException e) {
			}
			if (name.length() > 80)
				name = name.substring(0, 80);
				name = name.substring(0, name.length() - 1);


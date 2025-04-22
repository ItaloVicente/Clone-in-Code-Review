		filterText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				String filter = filterText.getText();
				if (filter.isEmpty()) {
					text.setText(info);
				} else {
					StringMatcher matcher = new StringMatcher('*' + filter + '*', true, false);

					StringBuilder filteredInfo = new StringBuilder();
					boolean previousLineEmpty = false;
					String delim = System.getProperty("line.separator"); //$NON-NLS-1$
					String[] infoLines = info.split(delim);
					for (String line : infoLines) {
						boolean lineEmpty = line.isEmpty();
						if (lineEmpty && previousLineEmpty) {
							continue;
						}
						if (lineEmpty || line.startsWith("***") || matcher.match(line)) { //$NON-NLS-1$
							previousLineEmpty = lineEmpty;
							filteredInfo.append(line).append(delim);
						}
					}
					text.setText(filteredInfo.toString());
				}
			}
		});


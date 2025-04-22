					int i = result.indexOf('.');
					if (i < 0) {
						Activator.handleError(
								UIText.ConfigurationEditorComponent_WrongNumberOfTokensMessage,
								null, true);
					} else {
						int j = result.lastIndexOf('.');
						String sectionName = result.substring(0, i);
						String subSectionName = (j <= i + 1) ? null
								: result.substring(i + 1, j);
						String entryName = result.substring(j + 1);
						if (subSectionName != null
								&& subSectionName.isEmpty()) {
							subSectionName = null;

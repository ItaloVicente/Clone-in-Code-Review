						String sectionName = st.nextToken();
						String entryName = st.nextToken();
						Entry entry = ((GitConfig) tv.getInput()).getEntry(
								sectionName, null, entryName);
						if (entry == null)
							editableConfig.setString(sectionName, null,
									entryName, dlg.getValue());
						else
							entry.addValue(dlg.getValue());

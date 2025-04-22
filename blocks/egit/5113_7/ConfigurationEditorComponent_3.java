						String sectionName = st.nextToken();
						String subSectionName = st.nextToken();
						String entryName = st.nextToken();
						Entry entry = ((GitConfig) tv.getInput()).getEntry(
								sectionName, subSectionName, entryName);
						if (entry == null)
							editableConfig.setString(sectionName,
									subSectionName, entryName, dlg.getValue());
						else
							entry.addValue(dlg.getValue());

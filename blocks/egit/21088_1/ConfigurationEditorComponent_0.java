					} else {
						int n = st.countTokens();
						String sectionName = st.nextToken();
						StringBuilder b = new StringBuilder(st.nextToken());
						for (int i = 0; i < n - 3; i++) {
							b.append(DOT);
							b.append(st.nextToken());
						}
						String subSectionName = b.toString();
						String entryName = st.nextToken();
						Entry entry = ((GitConfig) tv.getInput()).getEntry(
								sectionName, subSectionName, entryName);
						if (entry == null)
							editableConfig.setString(sectionName,
									subSectionName, entryName, dlg.getValue());
						markDirty();
					}

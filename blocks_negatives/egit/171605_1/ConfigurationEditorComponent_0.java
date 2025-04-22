					StringTokenizer st = new StringTokenizer(result, DOT);
					if (st.countTokens() == 2) {
						String sectionName = st.nextToken();
						String entryName = st.nextToken();
						Entry entry = ((GitConfig) tv.getInput()).getEntry(
								sectionName, null, entryName);
						if (entry == null)
							editableConfig.setString(sectionName, null,
									entryName, dlg.getValue());
						else
							entry.addValue(dlg.getValue());
						markDirty();
						reveal(sectionName, null, entryName);
					} else if (st.countTokens() > 2) {
						int n = st.countTokens();
						String sectionName = st.nextToken();
						StringBuilder b = new StringBuilder(st.nextToken());
						for (int i = 0; i < n - 3; i++) {
							b.append(DOT);
							b.append(st.nextToken());

						StringBuilder b = new StringBuilder(st.nextToken());
						for (int i = 0; i < n - 3; i++) {
							b.append(DOT);
							b.append(st.nextToken());
						}
						String subSectionName = b.toString();

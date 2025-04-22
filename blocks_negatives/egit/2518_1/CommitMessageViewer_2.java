				})) {
			@Override
			protected void writeHunkHeader(int aCur, int aEnd, int bCur,
					int bEnd) throws IOException {
				flush();
				int start = d.length();
				super.writeHunkHeader(aCur, aEnd, bCur, bEnd);
				flush();
				int end = d.length();
				styles.add(new StyleRange(start, end - start,
						sys_hunkHeaderColor, null));
			}

			@Override
			protected void writeAddedLine(RawText b, int bCur)
					throws IOException {
				flush();
				int start = d.length();
				super.writeAddedLine(b, bCur);
				flush();
				int end = d.length();
				styles.add(new StyleRange(start, end - start,
						sys_linesAddedColor, null));
			}

			@Override
			protected void writeRemovedLine(RawText b, int bCur)
					throws IOException {
				flush();
				int start = d.length();
				super.writeRemovedLine(b, bCur);
				flush();
				int end = d.length();
				styles.add(new StyleRange(start, end - start,
						sys_linesRemovedColor, null));
			}
		};

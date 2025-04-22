					List<StyleRange> styleRanges = new ArrayList<>();
					styleRanges.add(newStyleRange(0, filenameLen, null,
							isActiveEditor(ref.getModel()) ? FontType.ITALIC : FontType.REGULAR));
					styleRanges.add(
							newStyleRange(filenameLen + 1, text.length(),
									Display.getDefault().getSystemColor(SWT.COLOR_DARK_GRAY),
									isActiveEditor(ref.getModel()) ? FontType.ITALIC : FontType.REGULAR));
					if (getMatcher() != null) {
						for (Position pos : searchPattern.getMatches(filename)) {
							styleRanges.add(
									newStyleRange(pos.getStart(), pos.getEnd() - pos.getStart(), null,
									FontType.BOLD));
						}
					}
					cell.setStyleRanges(styleRanges.toArray(new StyleRange[] {}));

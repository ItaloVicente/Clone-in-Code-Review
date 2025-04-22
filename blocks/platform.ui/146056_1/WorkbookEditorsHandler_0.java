
					SearchPattern matcher = WorkbookEditorsHandler.this.getMatcher();
					if (matcher != null) {
						String pattern = matcher.getPattern();

						StyledStringHighlighter ssh = new StyledStringHighlighter();
						StyledString ss = ssh.highlight(text, pattern,
								new BoldStylerProvider(WorkbookEditorsHandler.this.getFont(false, true))
										.getBoldStyler());

						cell.setStyleRanges(ss.getStyleRanges());
					}

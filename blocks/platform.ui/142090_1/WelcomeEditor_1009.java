					StyledText text = (StyledText) texts.get(i);
					Point p1 = text.computeSize(extent, SWT.DEFAULT, false);
					((GridData) text.getLayoutData()).widthHint = p1.x;
				}
				Point p2 = infoArea.computeSize(SWT.DEFAULT, SWT.DEFAULT,
						true);
				scrolledComposite.setMinHeight(p2.y);

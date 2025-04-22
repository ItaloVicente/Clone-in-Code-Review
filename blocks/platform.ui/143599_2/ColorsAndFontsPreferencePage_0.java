							text.append(SPACE);
							text.append(data.getName() + SPACE);
							text.append(data.getHeight() + SPACE);
							text.append(data.getStyle() == SWT.NORMAL ? RESOURCE_BUNDLE.getString("normalFont") + SPACE //$NON-NLS-1$
									: EMPTY);
							text.append((data.getStyle() & SWT.BOLD) == SWT.BOLD

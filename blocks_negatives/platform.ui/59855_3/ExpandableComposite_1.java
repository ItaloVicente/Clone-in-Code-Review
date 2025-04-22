				if (client != null) {
					Point dsize = null;
					Control desc = getDescriptionControl();
					if (desc != null) {
						dsize = descriptionCache.computeSize(areaWidth,
								SWT.DEFAULT);
						y += descriptionVerticalSpacing;
						descriptionCache.setBounds(cx, y, areaWidth, dsize.y);
						y += dsize.y + clientVerticalSpacing;
					} else {
						y += clientVerticalSpacing;
						if (getSeparatorControl() != null)
							y -= VSPACE;

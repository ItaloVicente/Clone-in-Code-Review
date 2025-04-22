				Color[] colors = CSSSWTColorHelper.getSWTColors(grad,
						folder.getDisplay(), engine);
				int[] percents = CSSSWTColorHelper.getPercents(grad);

				if ("selected".equals(pseudo)) {
					folder.setSelectionBackground(colors, percents, true);
				} else {
					folder.setBackground(colors, percents, true);
				}


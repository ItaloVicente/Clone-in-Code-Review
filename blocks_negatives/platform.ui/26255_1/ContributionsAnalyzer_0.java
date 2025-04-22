			MMenuContribution toContribute = null;
			for (MMenuContribution item : slot) {
				if (toContribute == null) {
					toContribute = item;
					continue;
				}
				Object[] array = item.getChildren().toArray();
				for (int c = 0; c < array.length; c++) {
					MMenuElement me = (MMenuElement) array[c];
					if (!containsMatching(toContribute.getChildren(), me)) {
						toContribute.getChildren().add(me);
					}
				}
			}

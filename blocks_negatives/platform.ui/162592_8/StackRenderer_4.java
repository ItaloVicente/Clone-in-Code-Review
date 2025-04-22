			if (needsMenu || needsTB) {
				tabFolder.getTopRight().setData(THE_PART_KEY, part);
				tabFolder.getTopRight().pack(true);
				tabFolder.getTopRight().setVisible(true);
			} else {
				tabFolder.getTopRight().setData(THE_PART_KEY, null);
				tabFolder.getTopRight().setVisible(false);
			}

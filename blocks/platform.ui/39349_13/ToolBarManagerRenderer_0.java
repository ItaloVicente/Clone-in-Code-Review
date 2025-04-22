			if (parent == null) {
				ici.setVisible(itemModel.isVisible());
				return;
			}

			IContributionManagerOverrides ov = parent.getOverrides();
			if (ov == null) {
				ici.setVisible(itemModel.isVisible());
			} else {
				Boolean visible = ov.getVisible(ici);
				if (visible == null) {
					ici.setVisible(itemModel.isVisible());

			if (markers != null) {
				StringBuilder buffer = new StringBuilder();
				ILabelProvider provider = (ILabelProvider) getViewer()
						.getLabelProvider();
				for (int i = 0; i < markers.length; i++) {
					if (i > 0) {

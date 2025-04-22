
			private FillLayout createFillLayout() {
				FillLayout layout = new FillLayout();
				layout.marginWidth = 2;
				return layout;
			}

			private void onMouseMove(Event event) {
				if (tip == null)
					return;
				tip.dispose();
				tip = null;
				label = null;
			}

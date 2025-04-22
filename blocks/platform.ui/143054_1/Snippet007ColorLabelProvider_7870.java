				@Override
				public void dispose() {
					super.dispose();
					female.dispose();
				}
			}
			viewer.setLabelProvider(new ColorLabelProvider(attributes));

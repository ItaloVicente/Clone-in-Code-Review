					new AccessibleAdapter() {
						@Override
						public void getName(AccessibleEvent e) {
							e.result = ((MUILabel) me).getLocalizedLabel();
						}
					});

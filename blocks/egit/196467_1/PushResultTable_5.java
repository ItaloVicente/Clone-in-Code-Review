				if (selected instanceof RefUpdateElement) {
					String toShow = getResult((RefUpdateElement) selected);
					if (!hookResult.isEmpty()) {
						toShow = hookResult + toShow;
					}
					text.setText(toShow);
				}

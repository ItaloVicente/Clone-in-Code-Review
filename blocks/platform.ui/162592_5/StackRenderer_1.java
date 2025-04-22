				newViewTB.setVisible(true);
				RowData newRd = null;
				newRd = (RowData) newViewTB.getLayoutData();
				if (newRd == null) {
					newRd = new RowData();
					newViewTB.setLayoutData(newRd);
				}
				newRd.exclude = false;

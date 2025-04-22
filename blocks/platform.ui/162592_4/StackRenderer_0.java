				ToolBar tb = (ToolBar) curPart.getToolbar().getWidget();
				if (tb != null) {
					tb.setVisible(false);
					RowData tbRd = (RowData) tb.getLayoutData();
					if (tbRd != null) {
						tbRd.exclude = true;
					}
				}

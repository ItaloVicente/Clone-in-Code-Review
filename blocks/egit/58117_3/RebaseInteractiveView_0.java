			}
			for (TreeViewerColumn col : dynamicColumns) {
				int width = col.getColumn().getWidth();
				planLayout.setColumnData(col.getColumn(),
						new ColumnWeightData(width, width));
			}
			planLayout.layout(planTreeViewer.getTree().getParent(), true);

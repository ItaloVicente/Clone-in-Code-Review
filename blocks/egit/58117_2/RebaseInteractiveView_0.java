			}
			for (Map.Entry<TreeViewerColumn, Integer> entry : dynamicColumns
					.entrySet()) {
				TreeColumn column = entry.getKey().getColumn();
				int weight = entry.getValue().intValue();
				planLayout.setColumnData(column,
						new ColumnWeightData(weight, column.getWidth()));
			}
			planLayout.layout(planTreeViewer.getTree().getParent(), true);

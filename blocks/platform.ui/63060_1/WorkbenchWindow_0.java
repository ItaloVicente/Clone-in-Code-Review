					MTrimBar trimBar = findBar(areaId);
					int index = -1;
					MToolControl beforeControl = created.get(beforeMe);
					if (beforeMe != null && beforeControl != null) {
						index = trimBar.getChildren().indexOf(beforeControl);
					}
					MToolControl trimControl = created.get(trim);
					if (trimControl == null) {
						trimControl = modelService.createModelElement(MToolControl.class);
						trimControl.setElementId(trim.getId());
						trimControl.setObject(trim);
						trimControl.setWidget(trim.getControl());
						created.put(trim, trimControl);
					}
					if (index > 0) {
						trimBar.getChildren().add(index, trimControl);
					} else {
						trimBar.getChildren().add(trimControl);
					}

				if (toolItem == null) {
					toolItem = MenuFactoryImpl.eINSTANCE.createOpaqueToolItem();
					toolItem.setElementId(item.getId());
					renderer.linkModelToContribution(toolItem, item);
				}
				if (item instanceof AbstractGroupMarker && container.getWidget() instanceof Control
						&& !((Control) container.getWidget()).isDisposed()) {
					toolItem.setVisible(isChildVisible(item));
					container.getChildren().remove(toolItem);

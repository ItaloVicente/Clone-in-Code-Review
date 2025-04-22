				if (toolItem == null) {
					toolItem = MenuFactoryImpl.eINSTANCE.createOpaqueToolItem();
					toolItem.setElementId(item.getId());
					renderer.linkModelToContribution(toolItem, item);
					container.getChildren().add(toolItem);
				}

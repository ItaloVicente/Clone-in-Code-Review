		List<MHandler> handlers = ms.findElements(searchRoot, MHandler.class,
				EModelService.ANYWHERE, new Selector() {
					@Override
					public boolean select(MApplicationElement element) {
						return element instanceof MHandler
								&& id.equals(element.getElementId());
					}
				});

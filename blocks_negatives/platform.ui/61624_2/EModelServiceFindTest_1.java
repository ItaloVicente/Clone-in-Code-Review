		List<MKeyBinding> elements = modelService.findElements(application,
				MKeyBinding.class, EModelService.ANYWHERE, new Selector() {
					@Override
					public boolean select(MApplicationElement element) {
						return (element instanceof MKeyBinding);
					}
				});

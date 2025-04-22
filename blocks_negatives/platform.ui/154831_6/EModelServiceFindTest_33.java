				MAddon.class, EModelService.ANYWHERE, new Selector() {
					@Override
					public boolean select(MApplicationElement element) {
						return (element instanceof MAddon);
					}
				});

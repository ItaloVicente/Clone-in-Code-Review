					new IWindowCloseHandler() {
						@Override
						public boolean close(MWindow window) {
							EPartService partService = window.getContext().get(EPartService.class);
							return partService.saveAll(true);
						}

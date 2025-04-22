				final IEclipseContext evalContext;
				if (mMenu instanceof MContext) {
					evalContext = ((MContext) mMenu).getContext();
				} else {
					evalContext = modelService.getContainingContext(mMenu);
				}
				MenuManagerRendererFilter.updateElementVisibility(mMenu, renderer, menu,
						evalContext, 2, true);

				private MTrimBar findBar(int areaId) {
					switch (areaId) {
					case ITrimManager.TOP:
						return getTopTrim();
					case ITrimManager.LEFT:
						return modelService.getTrim(model, SideValue.LEFT);
					case ITrimManager.RIGHT:
						return modelService.getTrim(model, SideValue.RIGHT);
					case ITrimManager.BOTTOM:
					default:
						return modelService.getTrim(model, SideValue.BOTTOM);
					}

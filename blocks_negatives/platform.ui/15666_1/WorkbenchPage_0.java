
			MPerspective persp = perspectiveStack.getSelectedElement();
			List<String> newIds = ModeledPageLayout.getIds(persp, ModeledPageLayout.ACTION_SET_TAG);
			EContextService contextService = window.getContext().get(EContextService.class);
			for (String id : newIds) {
				contextService.activateContext(id);
			}

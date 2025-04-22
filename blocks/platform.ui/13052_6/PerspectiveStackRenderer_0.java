			MPerspective selected = (MPerspective) tabElement.getParent()
					.getSelectedElement();
			if (selected != null) {
				IEclipseContext context = selected.getContext();
				context.get(EPartService.class).switchPerspective(selected);
			}

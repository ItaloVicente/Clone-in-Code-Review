			if (ref instanceof MContext) {
				IEclipseContext context = ((MContext) ref).getContext();
				IEclipseContext newParentContext = getContext(ph);
				if (context.getParent() != newParentContext) {
					context.setParent(newParentContext);
				}

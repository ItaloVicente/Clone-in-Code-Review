		visiblePerspective.setLabel(newDescriptor.getLabel());
		visiblePerspective.setTooltip(newDescriptor.getLabel());
		visiblePerspective.setElementId(newDescriptor.getId());
		if (newDescriptor instanceof PerspectiveDescriptor) {
			((PerspectiveDescriptor) newDescriptor).setHasCustomDefinition(true);
		}
		{
			modelToPerspectiveMapping.remove(visiblePerspective);
			getPerspective(visiblePerspective);
		}

		if (element instanceof Repository)
			return getSimpleTextFor((Repository) element);

		if (element instanceof RefNode)
			return getSimpleTextFor((RefNode) element);

		if (element instanceof Ref)
			return ((Ref) element).getName();

		if (element instanceof ProjectRecord)
			return ((ProjectRecord) element).getProjectLabel();

		if (element instanceof GitModelObject)
			return ((GitModelObject) element).getName();

		return super.getText(element);

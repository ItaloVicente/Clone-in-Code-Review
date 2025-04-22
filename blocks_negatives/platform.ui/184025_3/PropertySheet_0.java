		ISelectionProvider provider = part.getSite().getSelectionProvider();
		if (provider != null) {
			IPage dPage = createPropertySheetPage(getPageBook());
			return new PageRec(part, dPage);
		}

		return null;

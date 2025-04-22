		String message = e.getMessage();
		if (message == null)
			return new Object[] { new ErrorNode(parentNode, parentNode
					.getRepository(),
					UIText.RepositoriesViewContentProvider_ExceptionNodeText) };
		else
			return new Object[] { new ErrorNode(parentNode, parentNode
					.getRepository(), message) };

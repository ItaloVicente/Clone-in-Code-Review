		} else if (adaptableObject instanceof ISelection) {
			IStructuredSelection structuredSelection = SelectionUtils
					.getStructuredSelection((ISelection) adaptableObject);
			repository = SelectionUtils.getRepository(structuredSelection);
		} else {
			throw new IllegalStateException();
		}

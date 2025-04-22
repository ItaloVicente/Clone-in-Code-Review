			if (input instanceof IAdaptable) {
				IWorkbenchAdapter wbadapter = ((IAdaptable) input)
						.getAdapter(IWorkbenchAdapter.class);
				if (wbadapter != null) {
					setContentDescription(wbadapter.getLabel(input));
					return;
				}

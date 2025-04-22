				if (item instanceof PluginActionContributionItem) {
					ISelection s = null;
					if (s instanceof IStructuredSelection) {
						for (Iterator<?> it = ((IStructuredSelection) s).iterator(); it.hasNext();) {
							Object element = it.next();
							assertTrue(name + " selection not converted", selectionType.isInstance(element));
						}
					}
				}

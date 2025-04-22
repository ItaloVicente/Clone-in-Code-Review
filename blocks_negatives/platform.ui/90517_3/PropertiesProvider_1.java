					Object element = ((IStructuredSelection) selection)
							.getFirstElement();
					PropertyPageContributorManager.getManager().contribute(
							pageManager, element);
					List list = pageManager
							.getElements(PreferenceManager.PRE_ORDER);
					IPreferenceNode[] properties = (IPreferenceNode[]) list
							.toArray(new IPreferenceNode[list.size()]);
					for (int i = 0; i < properties.length; i++) {

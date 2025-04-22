				for (Object element3 : getElements(PreferenceManager.POST_ORDER)) {
IPreferenceNode element = (IPreferenceNode) element3;
if (category.equals(element.getId())) {
				parent = element;
				break;
}
}

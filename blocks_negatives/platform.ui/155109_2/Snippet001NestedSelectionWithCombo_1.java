	/**
	 * The View's model--the root of our Model graph for this particular GUI.
	 * <p>
	 * Typically each View class has a corresponding ViewModel class. The ViewModel
	 * is responsible for getting the objects to edit from the DAO. Since this
	 * snippet doesn't have any persistent objects to retrieve, this ViewModel just
	 * instantiates a model object to edit.
	 */
	static class ViewModel extends AbstractModelObject {

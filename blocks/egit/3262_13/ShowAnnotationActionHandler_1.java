package org.eclipse.egit.ui.internal.actions;

public class ShowAnnotationAction extends RepositoryAction {

	public ShowAnnotationAction() {
		super(ActionCommands.SHOW_ANNOTATION_ACTION,
				new ShowAnnotationActionHandler());
	}

}

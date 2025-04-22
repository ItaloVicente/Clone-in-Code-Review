package org.eclipse.e4.demo.e4photo;

import javax.inject.Named;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.impl.BasicFactoryImpl;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

public class AddNoteHandler {
	public static final String PERSISTED_STATE = AddNoteHandler.class.getName() + ".persisted";
	
	static int editors = 0;
	
	@Execute
	public void execute(IWorkbench workbench, @Named(IServiceConstants.ACTIVE_SELECTION) IResource selection, EPartService partService) {
		if (selection == null)
			return;
		System.out.println("AddNoteHandler called " + selection.toString());
		MPart exifPart = partService.findPart("ExifView");
		MPart editor = BasicFactoryImpl.eINSTANCE.createPart();
		editor.setLabel("Note");
		editor.setContributionURI("bundleclass://org.eclipse.e4.demo.e4photo/org.eclipse.e4.demo.e4photo.NoteEditor");
		editor.setElementId("org.eclipse.e4.demo.e4photo.noteEditor" + editors);
		editors++;
		
		IPath path = selection.getLocation();
		
		if ("txt".equalsIgnoreCase(path.getFileExtension()))
				return; // this is already a note
		
		
		path = path.removeFileExtension();
		path = path.addFileExtension("txt");
		editor.getPersistedState().put(PERSISTED_STATE, path.toString());
		
		exifPart.getParent().getChildren().add(editor);
	}
}

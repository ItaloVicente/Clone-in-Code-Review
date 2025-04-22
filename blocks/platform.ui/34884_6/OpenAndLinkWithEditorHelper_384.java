
package org.eclipse.ui;

public abstract class NavigationLocation implements INavigationLocation {

    private IWorkbenchPage page;

    private IEditorInput input;

    protected NavigationLocation(IEditorPart editorPart) {
        this.page = editorPart.getSite().getPage();
        this.input = editorPart.getEditorInput();
    }

    protected IEditorPart getEditorPart() {
        if (input == null) {
			return null;
		}
        return page.findEditor(input);
    }

    @Override
	public Object getInput() {
        return input;
    }

    @Override
	public String getText() {
        IEditorPart part = getEditorPart();
        if (part == null) {
			return new String();
		}
        return part.getTitle();
    }

    @Override
	public void setInput(Object input) {
        this.input = (IEditorInput) input;
    }

    @Override
	public void dispose() {
        releaseState();
    }

    @Override
	public void releaseState() {
        input = null;
    }
}

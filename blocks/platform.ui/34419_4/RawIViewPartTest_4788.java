package org.eclipse.ui.tests.api.workbenchpart;

import org.eclipse.core.commands.common.EventManager;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPartConstants;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PlatformUI;

public class RawIViewPart extends EventManager implements IViewPart {

	private IViewSite site;

	private String title = "SomeTitle";

	public RawIViewPart() {
		super();
	}

	@Override
	public IViewSite getViewSite() {
		return site;
	}

	public void setTitle(String newTitle) {
		title = newTitle;
		firePropertyChange(IWorkbenchPartConstants.PROP_TITLE);
	}

	@Override
	public void init(IViewSite site) {
		this.site = site;
	}

	@Override
	public void init(IViewSite site, IMemento memento) {
		this.site = site;
	}

	protected void firePropertyChange(final int propertyId) {
		Object[] array = getListeners();
		for (Object element : array) {
			final IPropertyListener l = (IPropertyListener) element;
			SafeRunner.run(new SafeRunnable() {
				@Override
				public void run() {
					l.propertyChanged(RawIViewPart.this, propertyId);
				}
			});
		}
	}

	@Override
	public void saveState(IMemento memento) {

	}

	@Override
	public void addPropertyListener(IPropertyListener listener) {
		addListenerObject(listener);
	}

	@Override
	public void createPartControl(Composite parent) {

	}

	@Override
	public void dispose() {

	}

	@Override
	public IWorkbenchPartSite getSite() {
		return site;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public Image getTitleImage() {
		return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_DEF_VIEW);
	}

	@Override
	public String getTitleToolTip() {
		return "blah";
	}

	@Override
	public void removePropertyListener(IPropertyListener l) {
		removeListenerObject(l);
	}

	@Override
	public void setFocus() {

	}

	@Override
	public Object getAdapter(Class adapter) {
		return null;
	}

}

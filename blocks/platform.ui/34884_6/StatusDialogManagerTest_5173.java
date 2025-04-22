
package org.eclipse.ui.tests.statushandlers;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.internal.statushandlers.IStatusDialogConstants;
import org.eclipse.ui.internal.statushandlers.LabelProviderWrapper;
import org.eclipse.ui.statushandlers.IStatusAdapterConstants;
import org.eclipse.ui.statushandlers.StatusAdapter;

import junit.framework.TestCase;

public class LabelProviderWrapperTest extends TestCase {

	private LabelProviderWrapper wrapper;
	private Map dialogState = new HashMap();
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		dialogState = new HashMap();
		dialogState.put(IStatusDialogConstants.STATUS_ADAPTERS, new HashSet());
		((Collection)dialogState.get(IStatusDialogConstants.STATUS_ADAPTERS)).add(Status.OK_STATUS);
		wrapper = new LabelProviderWrapper(dialogState);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		wrapper = null;
	}
	
	public void testDisposing(){
		final boolean[] disposed = new boolean[]{false};
		ITableLabelProvider provider = new ITableLabelProvider() {
			@Override
			public void removeListener(ILabelProviderListener listener) {
			}
			@Override
			public boolean isLabelProperty(Object element, String property) {
				return false;
			}
			@Override
			public void dispose() {
				disposed[0] = true;
			}
			@Override
			public void addListener(ILabelProviderListener listener) {
			}
			@Override
			public String getColumnText(Object element, int columnIndex) {
				return null;
			}
			@Override
			public Image getColumnImage(Object element, int columnIndex) {
				return null;
			}
		};
		dialogState.put(IStatusDialogConstants.CUSTOM_LABEL_PROVIDER, provider);
		dialogState.put(IStatusDialogConstants.MODALITY_SWITCH, Boolean.TRUE);
		wrapper.dispose();
		assertFalse("Provider should not be disposed during modality switch",
				disposed[0]);
		
		dialogState.put(IStatusDialogConstants.MODALITY_SWITCH, Boolean.FALSE);
		wrapper.dispose();
		assertTrue("Provider should be disposed", disposed[0]);
	}

	public void testImages(){
		StatusAdapter saError = new StatusAdapter(new Status(IStatus.ERROR, "org.eclipse.ui.tests", "errorMessage"));
		assertEquals(wrapper.getSWTImage(SWT.ICON_ERROR), wrapper.getImage(saError));
		
		StatusAdapter saWarning = new StatusAdapter(new Status(IStatus.WARNING, "org.eclipse.ui.tests", "warningMessage"));
		assertEquals(wrapper.getSWTImage(SWT.ICON_WARNING), wrapper.getImage(saWarning));
		
		StatusAdapter saInfo = new StatusAdapter(new Status(IStatus.INFO, "org.eclipse.ui.tests", "infoMessage"));
		assertEquals(wrapper.getSWTImage(SWT.ICON_INFORMATION), wrapper.getImage(saInfo));
		
		StatusAdapter cancelOK = new StatusAdapter(new Status(IStatus.CANCEL, "org.eclipse.ui.tests", "cancelMessage"));
		assertEquals(wrapper.getSWTImage(SWT.ICON_INFORMATION), wrapper.getImage(cancelOK));
		
		StatusAdapter saOK = new StatusAdapter(new Status(IStatus.OK, "org.eclipse.ui.tests", "okMessage"));
		assertEquals(wrapper.getSWTImage(SWT.ICON_INFORMATION), wrapper.getImage(saOK));
	}
	
	public void testProvidedText_1(){
		final String title = "title";
		final String message = "errorMessage";
		
		StatusAdapter saError = new StatusAdapter(new Status(IStatus.ERROR, "org.eclipse.ui.tests", message));
		saError.setProperty(IStatusAdapterConstants.TITLE_PROPERTY, title);
		
		assertEquals(title, wrapper.getMainMessage(saError));
		assertEquals(message, wrapper.getSecondaryMessage(saError));
		
		((Collection)dialogState.get(IStatusDialogConstants.STATUS_ADAPTERS)).add(Status.CANCEL_STATUS);
		
		assertEquals(message, wrapper.getMainMessage(saError));
		assertEquals(title, wrapper.getColumnText(saError, 0));
	}
	
	
	public void testDecorating(){
		dialogState.put(IStatusDialogConstants.DECORATOR, new ILabelDecorator() {
			@Override
			public void removeListener(ILabelProviderListener listener) {
				
			}
			
			@Override
			public boolean isLabelProperty(Object element, String property) {
				return false;
			}
			
			@Override
			public void dispose() {
				
			}
			
			@Override
			public void addListener(ILabelProviderListener listener) {
				
			}
			
			@Override
			public String decorateText(String text, Object element) {
				return "decorated"+text;
			}
			
			@Override
			public Image decorateImage(Image image, Object element) {
				return null;
			}
		});
		StatusAdapter saError = new StatusAdapter(new Status(IStatus.ERROR, "org.eclipse.ui.tests", "message"));
		saError.setProperty(IStatusAdapterConstants.TITLE_PROPERTY, "title");
		assertEquals("decoratedtitle", wrapper.getMainMessage(saError));
		assertEquals("decoratedmessage", wrapper.getSecondaryMessage(saError));
	}
}

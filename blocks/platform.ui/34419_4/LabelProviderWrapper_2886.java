package org.eclipse.ui.internal.statushandlers;

import com.ibm.icu.text.DateFormat;
import java.net.URL;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.progress.ProgressManager;
import org.eclipse.ui.internal.progress.ProgressMessages;
import org.eclipse.ui.progress.IProgressConstants;
import org.eclipse.ui.statushandlers.IStatusAdapterConstants;
import org.eclipse.ui.statushandlers.StatusAdapter;


public class LabelProviderWrapper extends ViewerComparator implements
		ITableLabelProvider {
	private class DefaultLabelProvider implements ITableLabelProvider {
		ResourceManager manager = new LocalResourceManager(JFaceResources
				.getResources());

		@Override
		public void addListener(ILabelProviderListener listener) {
		}

		@Override
		public void dispose() {
			manager.dispose();
		}

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			Image result = null;
			if (element != null) {
				StatusAdapter statusAdapter = ((StatusAdapter) element);
				Job job = (Job) (statusAdapter.getAdapter(Job.class));
				if (job != null) {
					result = getIcon(job);
				}
			}
			if (result != null && result.isDisposed()) {
				result = null;
			}
			return result;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			StatusAdapter statusAdapter = (StatusAdapter) element;
			String text = WorkbenchMessages.WorkbenchStatusDialog_ProblemOccurred;
			if (!isMulti()) {
				Job job = (Job) (statusAdapter.getAdapter(Job.class));
				if (job != null) {
					text = getPrimaryMessage(statusAdapter);
				} else {
					text = getSecondaryMessage(statusAdapter);
				}
			} else {
				Job job = (Job) (statusAdapter.getAdapter(Job.class));
				if (job != null) {
					text = job.getName();
				} else {
					text = getPrimaryMessage(statusAdapter);
				}
			}
			Long timestamp = (Long) statusAdapter
					.getProperty(IStatusAdapterConstants.TIMESTAMP_PROPERTY);

			if (timestamp != null && isMulti()) {
				String date = DateFormat.getDateTimeInstance(DateFormat.LONG,
						DateFormat.LONG)
						.format(new Date(timestamp.longValue()));
				return NLS.bind(ProgressMessages.JobInfo_Error, (new Object[] {
						text, date }));
			}
			return text;
		}

		private Image getIcon(Job job) {
			if (job != null) {
				Object property = job
						.getProperty(IProgressConstants.ICON_PROPERTY);

				if (property instanceof ImageDescriptor) {
					return manager.createImage((ImageDescriptor) property);
				} else if (property instanceof URL) {
					return manager.createImage(ImageDescriptor
							.createFromURL((URL) property));
				} else {
					return ProgressManager.getInstance().getIconFor(job);
				}
			}
			return null;
		}

		@Override
		public boolean isLabelProperty(Object element, String property) {
			return false;
		}

		@Override
		public void removeListener(ILabelProviderListener listener) {
		}
	}

	private ITableLabelProvider labelProvider;

	private ILabelDecorator messageDecorator;

	private Map dialogState;

	public LabelProviderWrapper(Map dialogState) {
		this.dialogState = dialogState;
	}

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return labelProvider.getColumnImage(element, columnIndex);
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		return getLabelProvider().getColumnText(element, columnIndex);
	}

	@Override
	public void addListener(ILabelProviderListener listener) {
		getLabelProvider().addListener(listener);
	}

	@Override
	public void dispose() {
		boolean modalitySwitch = ((Boolean) dialogState.get(IStatusDialogConstants.MODALITY_SWITCH))
				.booleanValue();
		if (!modalitySwitch) {
			getLabelProvider().dispose();
		}
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		return getLabelProvider().isLabelProperty(element, property);
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		getLabelProvider().removeListener(listener);
	}

	public Image getImage(StatusAdapter statusAdapter) {
		if (statusAdapter != null) {
			int severity = statusAdapter.getStatus().getSeverity();
			switch (severity) {
			case IStatus.OK:
			case IStatus.INFO:
			case IStatus.CANCEL:
				return getSWTImage(SWT.ICON_INFORMATION);
			case IStatus.WARNING:
				return getSWTImage(SWT.ICON_WARNING);
			default: /* IStatus.ERROR */
				return getSWTImage(SWT.ICON_ERROR);
			}
		}
		return getSWTImage(SWT.ICON_ERROR);
	}

	public Image getSWTImage(final int imageID) {
		return Display.getCurrent().getSystemImage(imageID);
	}

	public String getMainMessage(StatusAdapter statusAdapter) {
		if (!isMulti()) {
			Job job = (Job) (statusAdapter.getAdapter(Job.class));
			if (job != null) {
				return NLS
						.bind(
								WorkbenchMessages.WorkbenchStatusDialog_ProblemOccurredInJob,
								job.getName());
			}
			return getPrimaryMessage(statusAdapter);
		}
		if (isMulti()) {
			Job job = (Job) (statusAdapter.getAdapter(Job.class));
			if (job != null) {
				return getPrimaryMessage(statusAdapter);
			}

			return getSecondaryMessage(statusAdapter);
		}
		return WorkbenchMessages.WorkbenchStatusDialog_ProblemOccurred;
	}

	public String getPrimaryMessage(StatusAdapter statusAdapter) {
		Object property = statusAdapter
				.getProperty(IStatusAdapterConstants.TITLE_PROPERTY);
		if (property instanceof String) {
			String header = (String) property;
			if (header.trim().length() > 0) {
				return decorate(header, statusAdapter);
			}
		}
		IStatus status = statusAdapter.getStatus();
		if (status.getMessage() != null
				&& status.getMessage().trim().length() > 0) {
			return decorate(status.getMessage(), statusAdapter);
		}

		if (status.getChildren().length > 0) {
			return WorkbenchMessages.WorkbenchStatusDialog_StatusWithChildren;
		}

		Throwable t = status.getException();
		if (t != null) {
			if (t.getMessage() != null && t.getMessage().trim().length() > 0) {
				return decorate(t.getMessage(), statusAdapter);
			}
			return t.getClass().getName();
		}
		return WorkbenchMessages.WorkbenchStatusDialog_ProblemOccurred;
	}

	public String getSecondaryMessage(StatusAdapter statusAdapter) {
		String primary = getPrimaryMessage(statusAdapter);

		IStatus status = statusAdapter.getStatus();
		String message = status.getMessage();
		String decoratedMessage = message == null ? null : decorate(message,
				statusAdapter);
		if (message != null && message.trim().length() > 0
				&& !primary.equals(decoratedMessage)) {
			return decoratedMessage;
		}
		if (status.getChildren().length > 0
				&& !primary.equals(decoratedMessage)) {
			return WorkbenchMessages.WorkbenchStatusDialog_StatusWithChildren;
		}

		Throwable t = status.getException();
		if (t != null) {
			if (t.getMessage() != null) {
				String decoratedThrowable = decorate(t.getMessage(),
						statusAdapter);
				if (t.getMessage().trim().length() > 0
						&& !primary.equals(decoratedThrowable)) {
					return decoratedThrowable;
				}
			}
			String throwableName = t.getClass().getName();
			if (!primary.equals(throwableName)) {
				return throwableName;
			}
		}
		return WorkbenchMessages.WorkbenchStatusDialog_SeeDetails;
	}

	private String decorate(String string, StatusAdapter adapter) {
		messageDecorator = (ILabelDecorator) dialogState
				.get(IStatusDialogConstants.DECORATOR);
		if (messageDecorator != null) {
			string = messageDecorator.decorateText(string, adapter);
		}
		return string;
	}

	private int compare(StatusAdapter s1, StatusAdapter s2) {
		Long timestamp1 = ((Long) s1
				.getProperty(IStatusAdapterConstants.TIMESTAMP_PROPERTY));
		Long timestamp2 = ((Long) s2
				.getProperty(IStatusAdapterConstants.TIMESTAMP_PROPERTY));
		if (timestamp1 == null || timestamp2 == null
				|| (timestamp1.equals(timestamp2))) {
			String text1 = getColumnText(s1, 0);
			String text2 = getColumnText(s2, 0);
			return text1.compareTo(text2);
		}

		if (timestamp1.longValue() < timestamp2.longValue()) {
			return -1;
		}
		if (timestamp1.longValue() > timestamp2.longValue()) {
			return 1;
		}
		return 0;
	}

	@Override
	public int compare(Viewer testViewer, Object o1, Object o2) {
		if (o1 instanceof StatusAdapter && o2 instanceof StatusAdapter) {
			return compare((StatusAdapter) o1, (StatusAdapter) o2);
		}
		if (o1.hashCode() < o2.hashCode()) {
			return -1;
		}
		if (o2.hashCode() > o2.hashCode()) {
			return 1;
		}
		return 0;
	}

	private boolean isMulti() {
		return ((Collection) dialogState
				.get(IStatusDialogConstants.STATUS_ADAPTERS)).size() > 1;
	}

	public ITableLabelProvider getLabelProvider() {
		ITableLabelProvider temp = (ITableLabelProvider) dialogState
				.get(IStatusDialogConstants.CUSTOM_LABEL_PROVIDER);
		if (temp != null) {
			labelProvider = temp;
		}
		if (labelProvider == null) {
			labelProvider = new DefaultLabelProvider();
		}
		return labelProvider;
	}
}

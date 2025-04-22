package org.eclipse.e4.ui.progress.internal.legacy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.e4.ui.progress.IProgressConstants;

public class StatusUtil {

	protected static List flatten(IStatus aStatus) {
		List result = new ArrayList();

		if (aStatus.isMultiStatus()) {
			IStatus[] children = aStatus.getChildren();
			for (int i = 0; i < children.length; i++) {
				IStatus currentChild = children[i];
				if (currentChild.isMultiStatus()) {
					Iterator childStatiiEnum = flatten(currentChild).iterator();
					while (childStatiiEnum.hasNext()) {
						result.add(childStatiiEnum.next());
					}
				} else {
					result.add(currentChild);
				}
			}
		} else {
			result.add(aStatus);
		}

		return result;
	}

	protected static IStatus newStatus(IStatus[] stati, String message,
			Throwable exception) {

		Assert.isTrue(message != null);
	    Assert.isTrue(message.trim().length() != 0);

		return new MultiStatus(IProgressConstants.PLUGIN_ID, IStatus.ERROR,
				stati, message, exception);
	}

    public static Throwable getCause(Throwable exception) {
        Throwable cause = null;
        if (exception != null) {
            if (exception instanceof CoreException) {
                CoreException ce = (CoreException)exception;
                cause = ce.getStatus().getException();
            } else {
            	try {
            		Method causeMethod = exception.getClass().getMethod("getCause", new Class[0]); //$NON-NLS-1$
            		Object o = causeMethod.invoke(exception, new Object[0]);
            		if (o instanceof Throwable) {
            			cause = (Throwable) o;
            		}
            	}
            	catch (NoSuchMethodException e) {
            	} catch (IllegalArgumentException e) {
				} catch (IllegalAccessException e) {
				} catch (InvocationTargetException e) {
				}
            }

            if (cause == null) {
                cause = exception;
            }
        }

        return cause;
    }

	public static IStatus newStatus(int severity, String message,
            Throwable exception) {

        String statusMessage = message;
        if (message == null || message.trim().length() == 0) {
            if (exception.getMessage() == null) {
				statusMessage = exception.toString();
			} else {
				statusMessage = exception.getMessage();
			}
        }

        return new Status(severity, IProgressConstants.PLUGIN_ID, severity,
                statusMessage, getCause(exception));
    }

	public static IStatus newStatus(List children, String message,
			Throwable exception) {

		List flatStatusCollection = new ArrayList();
		Iterator iter = children.iterator();
		while (iter.hasNext()) {
			IStatus currentStatus = (IStatus) iter.next();
			Iterator childrenIter = flatten(currentStatus).iterator();
			while (childrenIter.hasNext()) {
				flatStatusCollection.add(childrenIter.next());
			}
		}

		IStatus[] stati = new IStatus[flatStatusCollection.size()];
		flatStatusCollection.toArray(stati);
		return newStatus(stati, message, exception);
	}

    public static String getLocalizedMessage(Throwable exception) {
        String message = exception.getLocalizedMessage();

        if (message != null) {
            return message;
        }

        if (exception instanceof CoreException) {
            CoreException ce = (CoreException)exception;
            return ce.getStatus().getMessage();
        }

        return "ERROR OCCURRED";
    }



}

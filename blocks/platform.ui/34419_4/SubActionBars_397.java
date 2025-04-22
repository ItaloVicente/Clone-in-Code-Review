package org.eclipse.ui;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.actions.SimpleWildcardTester;
import org.eclipse.ui.internal.ActionExpression;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;
import org.eclipse.ui.internal.util.Util;
import org.eclipse.ui.model.IWorkbenchAdapter;
import org.osgi.framework.Bundle;

public final class SelectionEnabler {

		public String className;

		public String nameFilter;

		public boolean recursive;
	}

	public static final int ANY_NUMBER = -2;

	private static final int HASH_CODE_NOT_COMPUTED = -1;

	private static final int HASH_FACTOR = 89;

	private static final int HASH_INITIAL = SelectionEnabler.class.getName()
			.hashCode();

	private static Class iTextSelectionClass = null;

	private static final String JFACE_TEXT_PLUG_IN = "org.eclipse.jface.text"; //$NON-NLS-1$

	public static final int MULTIPLE = -5;

	public static final int NONE = -4;

	public static final int NONE_OR_ONE = -3;

	public static final int ONE_OR_MORE = -1;

	private static final String TEXT_SELECTION_CLASS = "org.eclipse.jface.text.ITextSelection"; //$NON-NLS-1$

	private static boolean textSelectionPossible = true;

	public static final int UNKNOWN = 0;

	private static Class getTextSelectionClass() {
		if (iTextSelectionClass != null) {
			return iTextSelectionClass;
		}
		if (!textSelectionPossible) {
			return null;
		}

		Bundle bundle = Platform.getBundle(JFACE_TEXT_PLUG_IN);
		if (bundle == null || bundle.getState() == Bundle.UNINSTALLED) {
			textSelectionPossible = false;
			return null;
		}

		if (bundle.getState() == Bundle.INSTALLED) {
			textSelectionPossible = true;
			return null;
		}

		try {
			Class c = bundle.loadClass(TEXT_SELECTION_CLASS);
			iTextSelectionClass = c;
			return iTextSelectionClass;
		} catch (ClassNotFoundException e) {
			textSelectionPossible = false;
			return null;
		}
	}

	public static boolean verifyNameMatch(String name, String filter) {
		return SimpleWildcardTester.testWildcardIgnoreCase(filter, name);
	}

	private List classes = new ArrayList();

	private ActionExpression enablementExpression;

	private transient int hashCode = HASH_CODE_NOT_COMPUTED;

	private int mode = UNKNOWN;

	public SelectionEnabler(IConfigurationElement configElement) {
		super();
		if (configElement == null) {
			throw new IllegalArgumentException();
		}
		parseClasses(configElement);
	}

	@Override
	public final boolean equals(final Object object) {
		if (object instanceof SelectionEnabler) {
			final SelectionEnabler that = (SelectionEnabler) object;
			return Util.equals(this.classes, that.classes)
					&& Util.equals(this.enablementExpression,
							that.enablementExpression)
					&& Util.equals(this.mode, that.mode);
		}

		return false;
	}

	@Override
	public final int hashCode() {
		if (hashCode == HASH_CODE_NOT_COMPUTED) {
			hashCode = HASH_INITIAL * HASH_FACTOR + Util.hashCode(classes);
			hashCode = hashCode * HASH_FACTOR
					+ Util.hashCode(enablementExpression);
			hashCode = hashCode * HASH_FACTOR + Util.hashCode(mode);
			if (hashCode == HASH_CODE_NOT_COMPUTED) {
				hashCode++;
			}
		}
		return hashCode;
	}

	private boolean isEnabledFor(ISelection sel) {
		Object obj = sel;
		int count = sel.isEmpty() ? 0 : 1;

		if (verifySelectionCount(count) == false) {
			return false;
		}

		if (enablementExpression != null) {
			return enablementExpression.isEnabledFor(obj);
		}

		if (classes.isEmpty()) {
			return true;
		}
		if (obj instanceof IAdaptable) {
			IAdaptable element = (IAdaptable) obj;
			if (verifyElement(element) == false) {
				return false;
			}
		} else {
			return false;
		}

		return true;
	}

	private boolean isEnabledFor(ISelection sel, int count) {
		if (verifySelectionCount(count) == false) {
			return false;
		}

		if (enablementExpression != null) {
			return enablementExpression.isEnabledFor(sel);
		}

		if (classes.isEmpty()) {
			return true;
		}
		for (int i = 0; i < classes.size(); i++) {
			SelectionClass sc = (SelectionClass) classes.get(i);
			if (verifyClass(sel, sc.className)) {
				return true;
			}
		}
		return false;
	}

	private boolean isEnabledFor(IStructuredSelection ssel) {
		int count = ssel.size();

		if (verifySelectionCount(count) == false) {
			return false;
		}

		if (enablementExpression != null) {
			return enablementExpression.isEnabledFor(ssel);
		}

		if (classes.isEmpty()) {
			return true;
		}
		for (Iterator elements = ssel.iterator(); elements.hasNext();) {
			Object obj = elements.next();
			if (obj instanceof IAdaptable) {
				IAdaptable element = (IAdaptable) obj;
				if (verifyElement(element) == false) {
					return false;
				}
			} else {
				return false;
			}
		}

		return true;
	}

	public boolean isEnabledForSelection(ISelection selection) {
		if (mode == UNKNOWN) {
			return false;
		}

		if (selection == null) {
			selection = StructuredSelection.EMPTY;
		}


		if (selection instanceof IStructuredSelection) {
			return isEnabledFor((IStructuredSelection) selection);
		}

		Class tselClass = getTextSelectionClass();
		if (tselClass != null && tselClass.isInstance(selection)) {
			try {
				Method m = tselClass.getDeclaredMethod(
						"getLength", new Class[0]); //$NON-NLS-1$
				Object r = m.invoke(selection, new Object[0]);
				if (r instanceof Integer) {
					return isEnabledFor(selection, ((Integer) r).intValue());
				}
				return true;
			} catch (NoSuchMethodException e) {
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			}
		}

		return isEnabledFor(selection);
	}

	private void parseClasses(IConfigurationElement config) {
		String enablesFor = config
				.getAttribute(IWorkbenchRegistryConstants.ATT_ENABLES_FOR);
		if (enablesFor == null) {
			enablesFor = "*"; //$NON-NLS-1$
		}
		if (enablesFor.equals("*")) { //$NON-NLS-1$
			mode = ANY_NUMBER;
		} else if (enablesFor.equals("?")) { //$NON-NLS-1$
			mode = NONE_OR_ONE;
		} else if (enablesFor.equals("!")) { //$NON-NLS-1$
			mode = NONE;
		} else if (enablesFor.equals("+")) { //$NON-NLS-1$
			mode = ONE_OR_MORE;
		} else if (enablesFor.equals("multiple") //$NON-NLS-1$
				|| enablesFor.equals("2+")) { //$NON-NLS-1$
			mode = MULTIPLE;
		} else {
			try {
				mode = Integer.parseInt(enablesFor);
			} catch (NumberFormatException e) {
				mode = UNKNOWN;
			}
		}

		IConfigurationElement[] children = config
				.getChildren(IWorkbenchRegistryConstants.TAG_ENABLEMENT);
		if (children.length > 0) {
			enablementExpression = new ActionExpression(children[0]);
			return;
		}

		children = config
				.getChildren(IWorkbenchRegistryConstants.TAG_SELECTION);
		if (children.length > 0) {
			classes = new ArrayList();
			for (int i = 0; i < children.length; i++) {
				IConfigurationElement sel = children[i];
				String cname = sel
						.getAttribute(IWorkbenchRegistryConstants.ATT_CLASS);
				String name = sel
						.getAttribute(IWorkbenchRegistryConstants.ATT_NAME);
				SelectionClass sclass = new SelectionClass();
				sclass.className = cname;
				sclass.nameFilter = name;
				classes.add(sclass);
			}
		}
	}

	private boolean verifyClass(Object element, String className) {
		Class eclass = element.getClass();
		Class clazz = eclass;
		boolean match = false;
		while (clazz != null) {
			if (clazz.getName().equals(className)) {
				match = true;
				break;
			}
			Class[] interfaces = clazz.getInterfaces();
			for (int i = 0; i < interfaces.length; i++) {
				if (interfaces[i].getName().equals(className)) {
					match = true;
					break;
				}
			}
			if (match == true) {
				break;
			}
			clazz = clazz.getSuperclass();
		}
		return match;
	}

	private boolean verifyElement(IAdaptable element) {
		if (classes.isEmpty()) {
			return true;
		}
		for (int i = 0; i < classes.size(); i++) {
			SelectionClass sc = (SelectionClass) classes.get(i);
			if (verifyClass(element, sc.className) == false) {
				continue;
			}
			if (sc.nameFilter == null) {
				return true;
			}
			IWorkbenchAdapter de = (IWorkbenchAdapter) Util.getAdapter(element, IWorkbenchAdapter.class);
			if ((de != null)
					&& verifyNameMatch(de.getLabel(element), sc.nameFilter)) {
				return true;
			}
		}
		return false;
	}

	private boolean verifySelectionCount(int count) {
		if (count > 0 && mode == NONE) {
			return false;
		}
		if (count == 0 && mode == ONE_OR_MORE) {
			return false;
		}
		if (count > 1 && mode == NONE_OR_ONE) {
			return false;
		}
		if (count < 2 && mode == MULTIPLE) {
			return false;
		}
		if (mode > 0 && count != mode) {
			return false;
		}
		return true;
	}
}

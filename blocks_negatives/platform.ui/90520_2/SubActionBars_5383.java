/*******************************************************************************
 * Copyright (c) 2000, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.eclipse.core.runtime.Adapters;
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

/**
 * Determines the enablement status given a selection. This calculation is done
 * based on the definition of the <code>enablesFor</code> attribute,
 * <code>enablement</code> element, and the <code>selection</code> element
 * found in the <code>IConfigurationElement</code> provided.
 * <p>
 * This class can be instantiated by clients. It is not intended to be extended.
 * </p>
 *
 * @since 3.0
 *
 * Note: The dependency on org.eclipse.jface.text for ITextSelection must be
 * severed It may be possible to do with IActionFilter generic workbench
 * registers IActionFilter for "size" property against IStructuredSelection
 * workbench text registers IActionFilter for "size" property against
 * ITextSelection code here: sel.getAdapter(IActionFilter.class) As an interim
 * solution, use reflection to access selections implementing ITextSelection
 */
public final class SelectionEnabler {

	/* package */static class SelectionClass {
		public String className;

		public String nameFilter;

		public boolean recursive;
	}

	public static final int ANY_NUMBER = -2;

	/**
	 * The constant integer hash code value meaning the hash code has not yet
	 * been computed.
	 */
	private static final int HASH_CODE_NOT_COMPUTED = -1;

	/**
	 * A factor for computing the hash code for all schemes.
	 */
	private static final int HASH_FACTOR = 89;

	/**
	 * The seed for the hash code for all schemes.
	 */
	private static final int HASH_INITIAL = SelectionEnabler.class.getName()
			.hashCode();

	/**
	 * Cached value of <code>org.eclipse.jface.text.ITextSelection.class</code>;
	 * <code>null</code> if not initialized or not present.
	 */
	private static Class iTextSelectionClass = null;

	/**
	 * Hard-wired id of the JFace text plug-in (not on pre-req chain).
	 */

	public static final int MULTIPLE = -5;

	public static final int NONE = -4;

	public static final int NONE_OR_ONE = -3;

	public static final int ONE_OR_MORE = -1;

	/**
	 * Hard-wired fully qualified name of the text selection class (not on
	 * pre-req chain).
	 */

	/**
	 * Indicates whether the JFace text plug-in is even around. Without the
	 * JFace text plug-in, text selections are moot.
	 */
	private static boolean textSelectionPossible = true;

	public static final int UNKNOWN = 0;

	/**
	 * Returns <code>ITextSelection.class</code> or <code>null</code> if the
	 * class is not available.
	 *
	 * @return <code>ITextSelection.class</code> or <code>null</code> if
	 *         class not available
	 * @since 3.0
	 */
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

	/**
	 * Verifies that the given name matches the given wildcard filter. Returns
	 * true if it does.
	 *
	 * @param name
	 * @param filter
	 * @return <code>true</code> if there is a match
	 */
	public static boolean verifyNameMatch(String name, String filter) {
		return SimpleWildcardTester.testWildcardIgnoreCase(filter, name);
	}

	private List classes = new ArrayList();

	private ActionExpression enablementExpression;

	/**
	 * The hash code for this object. This value is computed lazily, and marked
	 * as invalid when one of the values on which it is based changes.
	 */
	private transient int hashCode = HASH_CODE_NOT_COMPUTED;

	private int mode = UNKNOWN;

	/**
	 * Create a new instance of the receiver.
	 *
	 * @param configElement
	 */
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

	/**
	 * Computes the hash code for this object based on the id.
	 *
	 * @return The hash code for this object.
	 */
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

	/**
	 * Returns true if given structured selection matches the conditions
	 * specified in the registry for this action.
	 */
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

	/**
	 * Returns true if given text selection matches the conditions specified in
	 * the registry for this action.
	 */
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

	/**
	 * Returns true if given structured selection matches the conditions
	 * specified in the registry for this action.
	 */
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

	/**
	 * Check if the receiver is enabled for the given selection.
	 *
	 * @param selection
	 * @return <code>true</code> if the given selection matches the conditions
	 *         specified in <code>IConfirgurationElement</code>.
	 */
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

	/**
	 * Parses registry element to extract mode and selection elements that will
	 * be used for verification.
	 */
	private void parseClasses(IConfigurationElement config) {
		String enablesFor = config
				.getAttribute(IWorkbenchRegistryConstants.ATT_ENABLES_FOR);
		if (enablesFor == null) {
		}
			mode = ANY_NUMBER;
			mode = NONE_OR_ONE;
			mode = NONE;
			mode = ONE_OR_MORE;
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
			for (IConfigurationElement sel : children) {
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

	/**
	 * Verifies if the element is an instance of a class with a given class
	 * name. If direct match fails, implementing interfaces will be tested, then
	 * recursively all superclasses and their interfaces.
	 */
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
			for (Class currentInterface : interfaces) {
				if (currentInterface.getName().equals(className)) {
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

	/**
	 * Verifies if the given element matches one of the selection requirements.
	 * Element must at least pass the type test, and optionally wildcard name
	 * match.
	 */
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
			IWorkbenchAdapter de = Adapters.adapt(element, IWorkbenchAdapter.class);
			if ((de != null)
					&& verifyNameMatch(de.getLabel(element), sc.nameFilter)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Compare selection count with requirements.
	 */
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

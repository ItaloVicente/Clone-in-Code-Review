package org.eclipse.ui.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionFilter;
import org.eclipse.ui.internal.util.BundleUtility;
import org.eclipse.ui.internal.util.Util;
import org.osgi.framework.Bundle;

public class ActionExpression {

	private static abstract class AbstractExpression {

		protected transient int expressionHashCode = HASH_CODE_NOT_COMPUTED;

		public String[] extractObjectClasses() {
			return null;
		}

		public abstract boolean isEnabledFor(Object object);

		public boolean isEnabledForExpression(Object object,
				String expressionType) {
			return false;
		}

		public Collection valuesForExpression(String expressionType) {
			return null;
		}
	}

	private static class AndExpression extends CompositeExpression {

		public AndExpression(IConfigurationElement element)
				throws IllegalStateException {
			super(element);
		}

		@Override
		public final boolean equals(final Object object) {
			if (object instanceof AndExpression) {
				final AndExpression that = (AndExpression) object;
				return Util.equals(this.list, that.list);
			}

			return false;
		}

		@Override
		public boolean isEnabledFor(Object object) {
			Iterator iter = list.iterator();
			while (iter.hasNext()) {
				AbstractExpression expr = (AbstractExpression) iter.next();
				if (!expr.isEnabledFor(object)) {
					return false;
				}
			}
			return true;
		}
	}

	private static abstract class CompositeExpression extends
			AbstractExpression {
		protected ArrayList list;

		public CompositeExpression(IConfigurationElement element)
				throws IllegalStateException {
			super();

			IConfigurationElement[] children = element.getChildren();
			if (children.length == 0) {
				throw new IllegalStateException(
						"Composite expression cannot be empty"); //$NON-NLS-1$
			}

			list = new ArrayList(children.length);
			for (int i = 0; i < children.length; i++) {
				String tag = children[i].getName();
				AbstractExpression expr = createExpression(children[i]);
				if (EXP_TYPE_OBJECT_CLASS.equals(tag)) {
					list.add(0, expr);
				} else {
					list.add(expr);
				}
			}
		}

		@Override
		public String[] extractObjectClasses() {
			Iterator iterator = list.iterator();
			List classNames = null;
			while (iterator.hasNext()) {
				AbstractExpression next = (AbstractExpression) iterator.next();
				String[] objectClasses = next.extractObjectClasses();
				if (objectClasses != null) {
					if (classNames == null) {
						classNames = new ArrayList();
					}
					for (int i = 0; i < objectClasses.length; i++) {
						classNames.add(objectClasses[i]);
					}
				}
			}
			if (classNames == null) {
				return null;
			}

			String[] returnValue = new String[classNames.size()];
			classNames.toArray(returnValue);
			return returnValue;
		}

		@Override
		public final int hashCode() {
			if (expressionHashCode == HASH_CODE_NOT_COMPUTED) {
				expressionHashCode = HASH_INITIAL * HASH_FACTOR + Util.hashCode(list);
				if (expressionHashCode == HASH_CODE_NOT_COMPUTED) {
					expressionHashCode++;
				}
			}
			return expressionHashCode;
		}

		@Override
		public boolean isEnabledForExpression(Object object,
				String expressionType) {
			Iterator iterator = list.iterator();
			while (iterator.hasNext()) {
				AbstractExpression next = (AbstractExpression) iterator.next();
				if (next.isEnabledForExpression(object, expressionType)) {
					return true;
				}
			}
			return false;
		}

		@Override
		public Collection valuesForExpression(String expressionType) {
			Iterator iterator = list.iterator();
			Collection allValues = null;
			while (iterator.hasNext()) {
				AbstractExpression next = (AbstractExpression) iterator.next();
				Collection values = next.valuesForExpression(expressionType);
				if (values != null) {
					if (allValues == null) {
						allValues = values;
					} else {
						allValues.addAll(values);
					}
				}

			}
			return allValues;
		}
	}

	private static class NotExpression extends SingleExpression {

		public NotExpression(IConfigurationElement element)
				throws IllegalStateException {
			super(element);
		}

		@Override
		public boolean isEnabledFor(Object object) {
			return !super.isEnabledFor(object);
		}
	}

	private static class ObjectClassExpression extends AbstractExpression {
		private String className;

		private boolean extracted;

		public ObjectClassExpression(IConfigurationElement element)
				throws IllegalStateException {
			super();

			className = element.getAttribute(ATT_NAME);
			if (className == null) {
				throw new IllegalStateException(
						"Object class expression missing name attribute"); //$NON-NLS-1$
			}
		}

		public ObjectClassExpression(String className) {
			super();

			if (className != null) {
				this.className = className;
			} else {
				throw new IllegalStateException(
						"Object class expression must have class name"); //$NON-NLS-1$
			}
		}

		private boolean checkInterfaceHierarchy(Class interfaceToCheck) {
			if (interfaceToCheck.getName().equals(className)) {
				return true;
			}
			Class[] superInterfaces = interfaceToCheck.getInterfaces();
			for (int i = 0; i < superInterfaces.length; i++) {
				if (checkInterfaceHierarchy(superInterfaces[i])) {
					return true;
				}
			}
			return false;
		}

		@Override
		public final boolean equals(final Object object) {
			if (object instanceof ObjectClassExpression) {
				final ObjectClassExpression that = (ObjectClassExpression) object;
				return Util.equals(this.className, that.className)
						&& Util.equals(this.extracted, that.extracted);
			}

			return false;
		}

		@Override
		public String[] extractObjectClasses() {
			extracted = true;
			return new String[] { className };
		}

		@Override
		public final int hashCode() {
			if (expressionHashCode == HASH_CODE_NOT_COMPUTED) {
				expressionHashCode = HASH_INITIAL * HASH_FACTOR
						+ Util.hashCode(className);
				expressionHashCode = expressionHashCode * HASH_FACTOR + Util.hashCode(extracted);
				if (expressionHashCode == HASH_CODE_NOT_COMPUTED) {
					expressionHashCode++;
				}
			}
			return expressionHashCode;
		}

		@Override
		public boolean isEnabledFor(Object object) {
			if (object == null) {
				return false;
			}
			if (extracted) {
				return true;
			}

			Class clazz = object.getClass();
			while (clazz != null) {
				if (clazz.getName().equals(className)) {
					return true;
				}

				Class[] interfaces = clazz.getInterfaces();
				for (int i = 0; i < interfaces.length; i++) {
					if (checkInterfaceHierarchy(interfaces[i])) {
						return true;
					}
				}

				clazz = clazz.getSuperclass();
			}

			return false;
		}

		@Override
		public boolean isEnabledForExpression(Object object,
				String expressionType) {
			if (expressionType.equals(EXP_TYPE_OBJECT_CLASS)) {
				return isEnabledFor(object);
			}
			return false;
		}
	}

	private static class ObjectStateExpression extends AbstractExpression {
		private String name;

		private String value;

		public ObjectStateExpression(IConfigurationElement element)
				throws IllegalStateException {
			super();

			name = element.getAttribute(ATT_NAME);
			value = element.getAttribute(ATT_VALUE);
			if (name == null || value == null) {
				throw new IllegalStateException(
						"Object state expression missing attribute"); //$NON-NLS-1$
			}
		}

		@Override
		public final boolean equals(final Object object) {
			if (object instanceof ObjectStateExpression) {
				final ObjectStateExpression that = (ObjectStateExpression) object;
				return Util.equals(this.name, that.name)
						&& Util.equals(this.value, that.value);
			}

			return false;
		}

		private IActionFilter getActionFilter(Object object) {
			return (IActionFilter)Util.getAdapter(object, IActionFilter.class);
		}

		@Override
		public final int hashCode() {
			if (expressionHashCode == HASH_CODE_NOT_COMPUTED) {
				expressionHashCode = HASH_INITIAL * HASH_FACTOR + Util.hashCode(name);
				expressionHashCode = expressionHashCode * HASH_FACTOR + Util.hashCode(value);
				if (expressionHashCode == HASH_CODE_NOT_COMPUTED) {
					expressionHashCode++;
				}
			}
			return expressionHashCode;
		}

		@Override
		public boolean isEnabledFor(Object object) {
			if (object == null) {
				return false;
			}

			if (preciselyMatches(object)) {
				return true;
			}

			Class resourceClass = LegacyResourceSupport.getResourceClass();
			if (resourceClass == null) {
				return false;
			}

			if (resourceClass.isInstance(object)) {
				return false;
			}

			Object res = Util.getAdapter(object, resourceClass);
			if (res == null) {
				return false;
			}

			return preciselyMatches(res);

		}

		private boolean preciselyMatches(Object object) {
			IActionFilter filter = getActionFilter(object);
			if (filter == null) {
				return false;
			}

			return filter.testAttribute(object, name, value);
		}

		@Override
		public Collection valuesForExpression(String expressionType) {
			if (expressionType.equals(name)) {
				Collection returnValue = new HashSet();
				returnValue.add(value);
				return returnValue;
			}
			return null;
		}

	}

	private static class OrExpression extends CompositeExpression {

		public OrExpression(IConfigurationElement element)
				throws IllegalStateException {
			super(element);
		}

		@Override
		public final boolean equals(final Object object) {
			if (object instanceof OrExpression) {
				final OrExpression that = (OrExpression) object;
				return Util.equals(this.list, that.list);
			}

			return false;
		}

		@Override
		public boolean isEnabledFor(Object object) {
			Iterator iter = list.iterator();
			while (iter.hasNext()) {
				AbstractExpression expr = (AbstractExpression) iter.next();
				if (expr.isEnabledFor(object)) {
					return true;
				}
			}
			return false;
		}
	}

	private static class PluginStateExpression extends AbstractExpression {
		private String id;

		private String value;

		public PluginStateExpression(IConfigurationElement element)
				throws IllegalStateException {
			super();

			id = element.getAttribute(ATT_ID);
			value = element.getAttribute(ATT_VALUE);
			if (id == null || value == null) {
				throw new IllegalStateException(
						"Plugin state expression missing attribute"); //$NON-NLS-1$
			}
		}

		@Override
		public final boolean equals(final Object object) {
			if (object instanceof PluginStateExpression) {
				final PluginStateExpression that = (PluginStateExpression) object;
				return Util.equals(this.id, that.id)
						&& Util.equals(this.value, that.value);
			}

			return false;
		}

		@Override
		public final int hashCode() {
			if (expressionHashCode == HASH_CODE_NOT_COMPUTED) {
				expressionHashCode = HASH_INITIAL * HASH_FACTOR + Util.hashCode(id);
				expressionHashCode = expressionHashCode * HASH_FACTOR + Util.hashCode(value);
				if (expressionHashCode == HASH_CODE_NOT_COMPUTED) {
					expressionHashCode++;
				}
			}
			return expressionHashCode;
		}

		@Override
		public boolean isEnabledFor(Object object) {
			Bundle bundle = Platform.getBundle(id);
			if (!BundleUtility.isReady(bundle)) {
				return false;
			}
			if (value.equals(PLUGIN_INSTALLED)) {
				return true;
			}
			if (value.equals(PLUGIN_ACTIVATED)) {
				return BundleUtility.isActivated(bundle);
			}
			return false;
		}
	}

	private static class SingleExpression extends AbstractExpression {
		private AbstractExpression child;

		public SingleExpression(AbstractExpression expression)
				throws IllegalStateException {
			super();

			if (expression != null) {
				child = expression;
			} else {
				throw new IllegalStateException(
						"Single expression must contain 1 expression"); //$NON-NLS-1$
			}
		}

		public SingleExpression(IConfigurationElement element)
				throws IllegalStateException {
			super();

			IConfigurationElement[] children = element.getChildren();
			if (children.length != 1) {
				throw new IllegalStateException(
						"Single expression does not contain only 1 expression"); //$NON-NLS-1$
			}
			child = createExpression(children[0]);
		}

		@Override
		public final boolean equals(final Object object) {
			if (object instanceof SingleExpression) {
				final SingleExpression that = (SingleExpression) object;
				return Util.equals(this.child, that.child);
			}

			return false;
		}

		@Override
		public String[] extractObjectClasses() {
			return child.extractObjectClasses();
		}

		@Override
		public final int hashCode() {
			if (expressionHashCode == HASH_CODE_NOT_COMPUTED) {
				expressionHashCode = HASH_INITIAL * HASH_FACTOR + Util.hashCode(child);
				if (expressionHashCode == HASH_CODE_NOT_COMPUTED) {
					expressionHashCode++;
				}
			}
			return expressionHashCode;
		}

		@Override
		public boolean isEnabledFor(Object object) {
			return child.isEnabledFor(object);
		}

		@Override
		public boolean isEnabledForExpression(Object object,
				String expressionType) {
			return child.isEnabledForExpression(object, expressionType);
		}

		@Override
		public Collection valuesForExpression(String expressionType) {
			return child.valuesForExpression(expressionType);
		}

	}

	private static class SystemPropertyExpression extends AbstractExpression {
		private String name;

		private String value;

		public SystemPropertyExpression(IConfigurationElement element)
				throws IllegalStateException {
			super();

			name = element.getAttribute(ATT_NAME);
			value = element.getAttribute(ATT_VALUE);
			if (name == null || value == null) {
				throw new IllegalStateException(
						"System property expression missing attribute"); //$NON-NLS-1$
			}
		}

		@Override
		public boolean isEnabledFor(Object object) {
			String str = System.getProperty(name);
			if (str == null) {
				return false;
			}
			return value.equals(str);
		}

		@Override
		public final boolean equals(final Object object) {
			if (object instanceof SystemPropertyExpression) {
				final SystemPropertyExpression that = (SystemPropertyExpression) object;
				return Util.equals(this.name, that.name)
						&& Util.equals(this.value, that.value);
			}

			return false;
		}

		@Override
		public final int hashCode() {
			if (expressionHashCode == HASH_CODE_NOT_COMPUTED) {
				expressionHashCode = HASH_INITIAL * HASH_FACTOR + Util.hashCode(name);
				expressionHashCode = expressionHashCode * HASH_FACTOR + Util.hashCode(value);
				if (expressionHashCode == HASH_CODE_NOT_COMPUTED) {
					expressionHashCode++;
				}
			}
			return expressionHashCode;
		}
	}

	private static final String ATT_ID = "id"; //$NON-NLS-1$

	private static final String ATT_NAME = "name"; //$NON-NLS-1$

	private static final String ATT_VALUE = "value"; //$NON-NLS-1$

	public static final String EXP_TYPE_AND = "and"; //$NON-NLS-1$

	public static final String EXP_TYPE_NOT = "not"; //$NON-NLS-1$

	public static final String EXP_TYPE_OBJECT_CLASS = "objectClass"; //$NON-NLS-1$

	public static final String EXP_TYPE_OBJECT_STATE = "objectState"; //$NON-NLS-1$

	public static final String EXP_TYPE_OR = "or"; //$NON-NLS-1$

	public static final String EXP_TYPE_PLUG_IN_STATE = "pluginState"; //$NON-NLS-1$

	public static final String EXP_TYPE_SYSTEM_PROPERTY = "systemProperty"; //$NON-NLS-1$	

	private static final int HASH_CODE_NOT_COMPUTED = -1;

	private static final int HASH_FACTOR = 89;

	private static final int HASH_INITIAL = ActionExpression.class.getName()
			.hashCode();

	private static final String PLUGIN_ACTIVATED = "activated"; //$NON-NLS-1$

	private static final String PLUGIN_INSTALLED = "installed"; //$NON-NLS-1$

	private static AbstractExpression createExpression(
			IConfigurationElement element) throws IllegalStateException {
		String tag = element.getName();
		if (tag.equals(EXP_TYPE_OR)) {
			return new OrExpression(element);
		}
		if (tag.equals(EXP_TYPE_AND)) {
			return new AndExpression(element);
		}
		if (tag.equals(EXP_TYPE_NOT)) {
			return new NotExpression(element);
		}
		if (tag.equals(EXP_TYPE_OBJECT_STATE)) {
			return new ObjectStateExpression(element);
		}
		if (tag.equals(EXP_TYPE_OBJECT_CLASS)) {
			return new ObjectClassExpression(element);
		}
		if (tag.equals(EXP_TYPE_PLUG_IN_STATE)) {
			return new PluginStateExpression(element);
		}
		if (tag.equals(EXP_TYPE_SYSTEM_PROPERTY)) {
			return new SystemPropertyExpression(element);
		}

		throw new IllegalStateException(
				"Action expression unrecognized element: " + tag); //$NON-NLS-1$
	}

	private transient int hashCode = HASH_CODE_NOT_COMPUTED;

	private SingleExpression root;

	public ActionExpression(IConfigurationElement element) {
		try {
			root = new SingleExpression(element);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			root = null;
		}
	}

	public ActionExpression(String expressionType, String expressionValue) {
		if (expressionType.equals(EXP_TYPE_OBJECT_CLASS)) {
			root = new SingleExpression(new ObjectClassExpression(
					expressionValue));
		}
	}

	@Override
	public final boolean equals(final Object object) {
		if (object instanceof ActionExpression) {
			final ActionExpression that = (ActionExpression) object;
			return Util.equals(this.root, that.root);
		}

		return false;
	}

	public String[] extractObjectClasses() {
		return root.extractObjectClasses();
	}

	@Override
	public final int hashCode() {
		if (hashCode == HASH_CODE_NOT_COMPUTED) {
			hashCode = HASH_INITIAL * HASH_FACTOR + Util.hashCode(root);
			if (hashCode == HASH_CODE_NOT_COMPUTED) {
				hashCode++;
			}
		}
		return hashCode;
	}

	public boolean isEnabledFor(IStructuredSelection selection) {
		if (root == null) {
			return false;
		}

		if (selection == null || selection.isEmpty()) {
			return root.isEnabledFor(null);
		}

		Iterator elements = selection.iterator();
		while (elements.hasNext()) {
			if (!isEnabledFor(elements.next())) {
				return false;
			}
		}
		return true;
	}

	public boolean isEnabledFor(Object object) {
		if (root == null) {
			return false;
		}
		return root.isEnabledFor(object);
	}

	public boolean isEnabledForExpression(Object object, String expressionType) {
		if (root == null) {
			return false;
		}
		return root.isEnabledForExpression(object, expressionType);
	}

	public Collection valuesForExpression(String expressionType) {
		return root.valuesForExpression(expressionType);
	}
}

package org.eclipse.ui.internal.wizards.datatransfer.expressions;

import org.eclipse.core.expressions.EvaluationResult;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

public class HasFileWithSuffixRecursivelyExpression extends Expression {

	public static final String TAG = "hasFileWithSuffixRecursively"; //$NON-NLS-1$

	private String suffix;

	public HasFileWithSuffixRecursivelyExpression(String suffix) {
		this.suffix = suffix;
	}

	public HasFileWithSuffixRecursivelyExpression(IConfigurationElement element) {
		this(element.getAttribute("suffix")); //$NON-NLS-1$
	}

	@Override
	public EvaluationResult evaluate(IEvaluationContext context) throws CoreException {
		Object root = context.getDefaultVariable();
		IContainer container = Adapters.adapt(root, IContainer.class);
		if (container != null) {
			RecursiveSuffixFileFinder finder = new RecursiveSuffixFileFinder();
			container.accept(finder);
			return EvaluationResult.valueOf(finder.foundFileWithSuffix());
		}
		return EvaluationResult.FALSE;
	}

	private class RecursiveSuffixFileFinder implements IResourceVisitor {

		private boolean res = false;

		@Override
		public boolean visit(IResource resource) {
			if (resource.getType() == IResource.FILE && resource.getName().endsWith(HasFileWithSuffixRecursivelyExpression.this.suffix)) {
				this.res = true;
			}
			if (this.res) {
				return false;
			}
			return resource instanceof IContainer;
		}

		public boolean foundFileWithSuffix() {
			return this.res;
		}
	}
}

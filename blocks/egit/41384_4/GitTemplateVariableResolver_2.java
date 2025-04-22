package org.eclipse.egit.ui.internal.variables;

import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateVariable;
import org.eclipse.jface.text.templates.TemplateVariableResolver;

public class GitJavaTemplateVariableResolver extends TemplateVariableResolver {
	@Override
	public void resolve(TemplateVariable variable, TemplateContext context) {
		GitTemplateVariableResolverHelper.resolve(variable, context);
	}
}

package org.eclipse.egit.ui.internal.variables;

public class GitJavaTemplateVariableResolver extends org.eclipse.jface.text.templates.TemplateVariableResolver {
	@Override
	public void resolve(org.eclipse.jface.text.templates.TemplateVariable variable, org.eclipse.jface.text.templates.TemplateContext context) {
		GitTemplateVariableResolverHelper.resolve(variable, context);
	}
}

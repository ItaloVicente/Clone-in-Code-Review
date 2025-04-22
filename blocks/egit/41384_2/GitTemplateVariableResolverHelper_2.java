package org.eclipse.egit.ui.internal.variables;

import org.eclipse.egit.ui.internal.UIText;

public class GitTemplateVariableResolver extends org.eclipse.jface.text.templates.TemplateVariableResolver {

	public GitTemplateVariableResolver() {
		super("git_config", //$NON-NLS-1$
				UIText.GitTemplateVariableResolver_GitConfigDescription);
	}

	@Override
	public void resolve(org.eclipse.jface.text.templates.TemplateVariable variable, org.eclipse.jface.text.templates.TemplateContext context) {
		GitTemplateVariableResolverHelper.resolve(variable, context);
	}
}

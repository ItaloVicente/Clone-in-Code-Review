package org.eclipse.egit.ui.internal.variables;

import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateVariable;
import org.eclipse.jface.text.templates.TemplateVariableResolver;

public class GitTemplateVariableResolver extends TemplateVariableResolver {

	public GitTemplateVariableResolver() {
		super("git_config", //$NON-NLS-1$
				UIText.GitTemplateVariableResolver_GitConfigDescription);
	}

	@Override
	public void resolve(TemplateVariable variable, TemplateContext context) {
		GitTemplateVariableResolverHelper.resolve(variable, context);
	}
}

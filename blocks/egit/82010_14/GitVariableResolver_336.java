package org.eclipse.egit.ui.internal.variables;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.egit.core.project.RepositoryMapping;
import org.eclipse.egit.ui.Activator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.internal.corext.template.java.CodeTemplateContext;
import org.eclipse.jdt.internal.corext.template.java.CompilationUnitContext;
import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateVariable;
import org.eclipse.jface.text.templates.TemplateVariableResolver;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;

public class GitTemplateVariableResolver extends TemplateVariableResolver {

	public GitTemplateVariableResolver(String type, String description) {
		super(type, description);
	}

	public GitTemplateVariableResolver() {
		super();
	}

	@Override
	public void resolve(TemplateVariable variable, TemplateContext context) {
		resolveVariable(variable, context);
	}

	protected static void resolveVariable(TemplateVariable variable,
			IProject project) {
		final List<String> params = variable.getVariableType().getParams();
		if (params.isEmpty()) {
			variable.setValue(""); //$NON-NLS-1$
			return;
		}

		final String gitKey = params.get(0);
		if (gitKey == null || gitKey.length() == 0) {
			variable.setValue(""); //$NON-NLS-1$
			return;
		}

		RepositoryMapping mapping = RepositoryMapping.getMapping(project);
		Repository repository = null;

		if (mapping != null) {
			repository = mapping.getRepository();
		}
		if (repository == null) {
			variable.setValue(""); //$NON-NLS-1$
			return;
		}

		StoredConfig config = repository.getConfig();

		final String[] splits = gitKey.split("\\."); //$NON-NLS-1$
		String section = null;
		String subSection = null;
		String name = null;

		if (splits.length == 3) {
			section = splits[0];
			subSection = splits[1];
			name = splits[2];
		} else if (splits.length == 2) {
			section = splits[0];
			name = splits[1];
		} else {
			variable.setValue(""); //$NON-NLS-1$
			return;
		}

		String gitValue = config.getString(section, subSection, name);
		if (gitValue != null) {
			variable.setValue(gitValue);
		}
	}

	protected static void resolveVariable(TemplateVariable variable,
			TemplateContext context) {
		IProject project = getProject(context);
		if (project != null) {
			resolveVariable(variable, project);
		}
	}

	protected static IProject getProject(TemplateContext context) {
		IProject project = null;
		if (Activator.hasJavaPlugin()) {
			if (context instanceof CodeTemplateContext) {
				IJavaProject javaProject = ((CodeTemplateContext) context)
						.getJavaProject();
				if (javaProject != null) {
					project = javaProject.getProject();
				}
			} else if (context instanceof CompilationUnitContext) {
				ICompilationUnit cu = ((CompilationUnitContext) context)
						.getCompilationUnit();
				if (cu != null) {
					project = cu.getJavaProject().getProject();
				}
			}
		}
		return project;
	}
}


	/**
	 * Retrieves the current project from a template context.
	 *
	 * @param context
	 *            the current template context.
	 * @return the current project
	 */
	@Nullable
	protected static IProject getProject(TemplateContext context) {
		if (IAdaptable.class.isInstance(context)) {
			return Adapters.adapt(context, IProject.class);
		}
		if (Activator.hasJavaPlugin()) {
			boolean hasPublicMethod = context.getClass().getSimpleName()
			try {
				Method method;
				if (hasPublicMethod) {
				} else {
					method = context.getClass().getSuperclass()
					method.setAccessible(true);
				}
				Object result = method.invoke(context);
				if (result instanceof IJavaProject) {
					IJavaProject javaProject = (IJavaProject) result;
					return javaProject.getProject();
				}
			} catch (NoSuchMethodException | SecurityException
					| IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				return null;
			}
		}
		return null;
	}

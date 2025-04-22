			boolean hasPublicMethod = context.getClass().getSimpleName()
					.equals("CodeTemplateContext"); //$NON-NLS-1$
			try {
				Method method;
				if (hasPublicMethod) {
					method = context.getClass().getMethod("getJavaProject"); //$NON-NLS-1$
				} else {
					method = context.getClass().getSuperclass()
							.getDeclaredMethod("getJavaProject"); //$NON-NLS-1$
					method.setAccessible(true);

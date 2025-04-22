			setterMethod = clazz.getMethod(getSetterName(fieldName),
					new Class[] { returnType });
			addPropertyListenerMethod = clazz
					.getMethod(
							"addPropertyChangeListener", new Class[] { IPropertyChangeListener.class }); //$NON-NLS-1$
			removePropertyListenerMethod = clazz
					.getMethod(
							"removePropertyChangeListener", new Class[] { IPropertyChangeListener.class }); //$NON-NLS-1$

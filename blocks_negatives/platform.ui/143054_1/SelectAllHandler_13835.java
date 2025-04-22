					final Method textLimitAccessor = focusControl.getClass()
							.getMethod("getTextLimit", NO_PARAMETERS); //$NON-NLS-1$
					final Integer textLimit = (Integer) textLimitAccessor
							.invoke(focusControl);
					final Object[] parameters = { new Point(0, textLimit
							.intValue()) };

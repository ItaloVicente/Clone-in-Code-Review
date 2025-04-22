					number = (Number) icuBigDecimalCtr
							.newInstance(new Object[] {
							o.unscaledValue(), new Integer(o.scale()) });
				} catch (InstantiationException e) {
				} catch (InvocationTargetException e) {
				} catch (IllegalAccessException e) {

			icuBigDecimalScale = icuBigDecimal.getMethod(
					"scale", (Class<?>[]) null); //$NON-NLS-1$
			icuBigDecimalUnscaledValue = icuBigDecimal.getMethod(
					"unscaledValue", (Class<?>[]) null); //$NON-NLS-1$
		} catch (ClassNotFoundException e) {
		} catch (NoSuchMethodException e) {

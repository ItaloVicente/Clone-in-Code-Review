				if (bd.scale() == 0)
					return bd;
				throw new IllegalArgumentException(
						"Non-integral Double value returned from NumberFormat " //$NON-NLS-1$
								+ "which cannot be accurately stored in a BigDecimal due " //$NON-NLS-1$
								+ "to lost precision. Consider using ICU4J or Java 5 which " //$NON-NLS-1$
								+ "can properly format and parse these types."); //$NON-NLS-1$

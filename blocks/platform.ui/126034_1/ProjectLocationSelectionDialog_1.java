			try {
				BigDecimal newNumber = new BigDecimal(m.group()).add(new BigDecimal(1));
				return m.replaceFirst(newNumber.toPlainString());
			} catch (NumberFormatException e) {
				return m.replaceFirst("2"); //$NON-NLS-1$
			}

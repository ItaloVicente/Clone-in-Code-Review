			String numberStr;
			BigDecimal newNumber = null;
			try {
				newNumber = new BigDecimal(m.group()).add(BigDecimal.valueOf(1));
				numberStr = m.replaceFirst(newNumber.toPlainString());
			} catch (NumberFormatException e) {
				numberStr = m.replaceFirst("2"); //$NON-NLS-1$
			}

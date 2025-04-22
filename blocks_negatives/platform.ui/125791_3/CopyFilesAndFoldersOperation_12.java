			String numberStr;
			BigDecimal newNumber = null;
			try {
				newNumber = new BigDecimal(m.group()).add(new BigDecimal(1));
				numberStr = m.replaceFirst(newNumber.toPlainString());
			} catch (NumberFormatException e) {
			}

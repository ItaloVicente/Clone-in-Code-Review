        propertyDescriptor.setValidator(value -> {
		    if (value == null)
		        return MessageUtil.getString("salary_is_invalid"); //$NON-NLS-1$
		    Float trySalary;
		    try {
		        trySalary = Float.valueOf(Float.parseFloat((String) value));
		    } catch (NumberFormatException e) {
		        return MessageUtil.getString("salary_is_invalid"); //$NON-NLS-1$
		    }
		    if (trySalary.floatValue() < 0.0)
		        return MessageUtil
		                .getString("salary_must_be_greator_than_zero"); //$NON-NLS-1$
		    return null;
		});

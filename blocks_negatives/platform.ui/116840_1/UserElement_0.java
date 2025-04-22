        propertyDescriptor.setValidator(new ICellEditorValidator() {
            @Override
			public String isValid(Object value) {
                if (value == null)
                Float trySalary;
                try {
                    trySalary = new Float(Float.parseFloat((String) value));
                } catch (NumberFormatException e) {
                }
                if (trySalary.floatValue() < 0.0)
                    return MessageUtil
                return null;
            }
        });

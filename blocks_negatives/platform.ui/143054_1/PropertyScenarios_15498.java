                String modelValue = (String) toObject;
                if (modelValue == null || modelValue.equals("")) {
                    return modelValue;
                }
                String firstChar = modelValue.substring(0, 1);
                String remainingChars = modelValue.substring(1);
                return firstChar.toUpperCase() + remainingChars.toLowerCase();
            }
        };
        IConverter converter2 = new IConverter() {
            @Override

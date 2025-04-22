        String setting = items.get(key);
        if (setting == null) {
            throw new NumberFormatException(
        }

		return Long.valueOf(setting).longValue();
    }

    @Override

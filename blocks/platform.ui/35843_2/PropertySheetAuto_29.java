            if (id.equals(MODEL_YEAR)) {
				modelYear = new Integer((String) value).intValue();
			}
            if (id.equals(COLOR)) {
				color = (RGB) value;
			}
            if (id.equals(MANUFACTURER)) {
				manufacturer = (String) value;
			}
            if (id.equals(MODEL)) {
				model = (String) value;
			}
            if (id.equals(ENGINE_SIZE)) {
				engineSize = new Double((String) value).doubleValue();
			}

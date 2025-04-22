
		for (int i = 0; i < value.length; totalbody++, i++) {
			buffer[HEADER_LENGTH + totalbody] = value[i];
		}

		for (int i = 0; i < HEADER_LENGTH; i++) {
			buffer[i] = mbytes[i];

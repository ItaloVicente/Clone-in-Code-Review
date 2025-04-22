
		ptr.value += OBJECT_ID_STRING_LENGTH;
		if (raw[ptr.value] != '\n') {
			ptr.value = nextLF(raw
			return false;
		}
		ptr.value++;
		return true;

		parseBase10(raw, emailE + 1, ptrout); // when
		ptr = ptrout.value;
		if (emailE + 1 == ptr)
			return -1;
		if (ptr == raw.length || raw[ptr] != ' ')
			return -1;

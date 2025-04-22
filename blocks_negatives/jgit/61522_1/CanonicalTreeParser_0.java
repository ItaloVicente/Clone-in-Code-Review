		prevPtr = -1;
		currPtr = 0;
		if (eof())
			nextPtr = 0;
		else
			parseEntry();

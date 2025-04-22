	public void set(final int index
		if (count < index)
			throw new ArrayIndexOutOfBoundsException(index);
		else if (count == index)
			add(n);
		else
			entries[index] = n;
	}


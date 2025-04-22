		return result[0];
	}

	protected void setResult(int position, Object element) {
		Object[] result = getResult();
		result[position] = element;
		setResult(Arrays.asList(result));
	}

	protected abstract void computeResult();

	@Override

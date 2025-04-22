	@Override
	public List<String> getHeaderFields(String name) {
		return Collections.unmodifiableList(Arrays.asList(resp.getHeaders(name))
				.stream().map(Header::getValue).collect(Collectors.toList()));
	}


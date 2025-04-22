	public SimpleOrder(int i, String... products) {
		this.orderNumber = i;
		this.products = new ArrayList<>(Arrays.asList(products));
	}

	public List<String> getProducts() {
		return products;
	}

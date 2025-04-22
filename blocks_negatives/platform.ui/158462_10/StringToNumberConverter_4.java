		super(String.class, toType, numberFormat);

		this.toType = Objects.requireNonNull(toType);
		this.numberFormat = Objects.requireNonNull(numberFormat);
		this.min = min;
		this.max = max;
		this.boxedType = Objects.requireNonNull(boxedType);

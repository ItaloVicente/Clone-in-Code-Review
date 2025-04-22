
		final IProduct product = Platform.getProduct();
		if (product == null )
			throw new ExecutionException("No product is defined."); //$NON-NLS-1$

		String aboutText = ProductProperties.getAboutText(product);
		String lines[] = aboutText.split("\\r?\\n"); //$NON-NLS-1$
		if (lines.length<=3){
			throw new ExecutionException("Product About Text is not properly defined."); //$NON-NLS-1$
		}

		String toCopy = String.format("%s%n%s%n%s", lines[0], lines[2], lines[3]); //$NON-NLS-1$

		Clipboard clipboard = new Clipboard(null);

		if (variantTreeProvider != null) {
			if (variantTreeProvider.getBaseTree() instanceof GitResourceVariantTree)
				((GitResourceVariantTree) variantTreeProvider.getBaseTree())
						.dispose();
			if (variantTreeProvider.getRemoteTree() instanceof GitResourceVariantTree)
				((GitResourceVariantTree) variantTreeProvider.getRemoteTree())
						.dispose();
			if (variantTreeProvider.getSourceTree() instanceof GitResourceVariantTree)
				((GitResourceVariantTree) variantTreeProvider.getSourceTree())
						.dispose();
			variantTreeProvider = null;
		}

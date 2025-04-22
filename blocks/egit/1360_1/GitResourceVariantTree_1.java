		}

		subMonitor.beginTask(NLS.bind(
				CoreText.GitResourceVariantTree_fetchingVariant,
				resource.getName()), IProgressMonitor.UNKNOWN);
		try {
			return fetchVariant(resource, subMonitor);
		} finally {
			subMonitor.done();
		}
	}

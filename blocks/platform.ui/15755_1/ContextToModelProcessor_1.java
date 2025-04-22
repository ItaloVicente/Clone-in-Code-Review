		for (MBindingContext mctx : contextFromModel) {
			if (!definedContexts.contains(mctx) && mctx.getContributorURI() != null
					&& mctx.getContributorURI().startsWith("platform:")) { //$NON-NLS-1$
				EObject parent = ((EObject) mctx).eContainer();
				if (parent instanceof MBindingContext) {
					((MBindingContext) parent).getChildren().remove(mctx);
				} else if (parent instanceof MApplication) {
					((MApplication) parent).getBindingContexts().remove(mctx);
				}
			}
		}

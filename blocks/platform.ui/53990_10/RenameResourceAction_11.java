				inlinedResource = null;
				disposeTextWidget();
				if (navigatorTree != null && !navigatorTree.isDisposed()) {
					navigatorTree.setFocus();
				}
			} finally {
				saving = false;

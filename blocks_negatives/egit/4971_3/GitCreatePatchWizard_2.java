			/**
			 * Avoid draw flicker by clearing error message if all is valid.
			 */
			if (pageValid) {
				setMessage(null);
				setErrorMessage(null);
			}
			setPageComplete(pageValid);
			return pageValid;

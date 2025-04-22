
		private class ResLabelProvider extends LabelProvider {

			public String getText(Object element) {
				final PreviewDecoration decoration = getDecoration(element);
				final StringBuilder buffer = new StringBuilder();
				final String prefix = decoration.getPrefix();
				if (prefix != null)
					buffer.append(prefix);
				buffer.append(((PreviewResource) element).getName());
				final String suffix = decoration.getSuffix();
				if (suffix != null)
					buffer.append(suffix);
				return buffer.toString();
			}

			public Image getImage(Object element) {
				final String s;
				switch (((PreviewResource) element).type) {
				case IResource.PROJECT:
					s = SharedImages.IMG_OBJ_PROJECT;
					break;
				case IResource.FOLDER:
					s = ISharedImages.IMG_OBJ_FOLDER;
					break;
				default:
					s = ISharedImages.IMG_OBJ_FILE;
					break;
				}
				final Image baseImage = PlatformUI.getWorkbench().getSharedImages()
						.getImage(s);
				final ImageDescriptor overlay = getDecoration(element).getOverlay();
				if (overlay == null)
					return baseImage;
				try {
					return fImageCache.createImage(new DecorationOverlayIcon(
							baseImage, overlay, IDecoration.BOTTOM_RIGHT));
				} catch (Exception e) {
					Activator.logError(e.getMessage(), e);
				}

				return null;
			}
		}
	}

	private static class GitModelCommitMokeup {

		private static final String message = "Commit message text"; //$NON-NLS-1$
		private static final String author = "Author Name"; //$NON-NLS-1$
		private static final Date date = new Date();
		private static final String committer = "Committer Name";  //$NON-NLS-1$

		public String getMokeupText(String format, String dateFormat) {
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

			Map<String, String> bindings = new HashMap<String, String>();
			bindings.put(GitChangeSetLabelProvider.BINDING_CHANGESET_DATE, sdf.format(date));
			bindings.put(GitChangeSetLabelProvider.BINDING_CHANGESET_AUTHOR, author);
			bindings.put(GitChangeSetLabelProvider.BINDING_CHANGESET_COMMITTER, committer);
			bindings.put(GitChangeSetLabelProvider.BINDING_CHANGESET_SHORT_MESSAGE, message);

			return GitChangeSetLabelProvider.formatName(format, bindings);
		}

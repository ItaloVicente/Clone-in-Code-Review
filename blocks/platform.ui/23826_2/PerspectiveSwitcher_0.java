			} else if (UIEvents.UILabel.ICONURI.equals(attName)) {
				Image currentImage = ti.getImage();
				if (currentImage != null)
					currentImage.dispose();
				String uri = (String) newValue;
				URL url = null;
				try {
					url = new URL(uri);
				} catch (MalformedURLException e) {
					e.printStackTrace();
					ti.setImage(null);
					return;
				}
				ImageDescriptor descriptor = ImageDescriptor.createFromURL(url);
				if (descriptor == null) {
					ti.setImage(null);
				} else {
					ti.setImage(descriptor.createImage());
				}

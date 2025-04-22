			super.fill(parent, index);

			ToolItem item = new ToolItem(parent, index);

			item.addDisposeListener(disposeListener);
			item.setImage(WorkbenchImages.getImage(ISharedImages.IMG_DEF_VIEW));
		}

		@Override

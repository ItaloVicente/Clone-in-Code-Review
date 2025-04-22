		Color peeledColor = null;
		if (ref.getPeeledObjectId() == null || !ref.getPeeledObjectId().equals(ref.getObjectId())) {
			peeledColor = new Color(g.getDevice(), ColorUtil.blend(g.getBackground().getRGB(), sys_white.getRGB()));
			g.setBackground(peeledColor);
		}


		try {
			Class<?> radialGradientPaintClass = Class
			Class<?>[] classes = radialGradientPaintClass.getClasses();
			int i;
			for (i = 0; i < classes.length; i++) {
						.equals(classes[i].getCanonicalName())) {
					break;
				}
			}
			Constructor<?> ctor = radialGradientPaintClass
					.getConstructor(new Class[] { java.awt.geom.Point2D.class,
							float.class, java.awt.geom.Point2D.class,
							float[].class, java.awt.Color[].class, classes[i] });

			final Object radialGradientPaint = ctor.newInstance(new Object[] {
					new Point2D.Double(width / 2.0, 0), width,
					new Point2D.Double(width / 2.0, 0.0), fractions,
					colorArray, classes[i].getEnumConstants()[0] });
			g2.setPaint((java.awt.Paint) radialGradientPaint);
		} catch (Exception e) {
			System.err
			.println("Warning - radial gradients are only supported in Java 6 and higher, using flat background color instead"); //$NON-NLS-1$
			g2.setColor(colorArray[0]);
		}

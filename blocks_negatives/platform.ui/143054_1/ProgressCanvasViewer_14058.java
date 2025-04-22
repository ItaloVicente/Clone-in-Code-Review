		        if (orientation == SWT.HORIZONTAL) {
		        	gc.drawString(string, 2, yOffset + (i * fontMetrics.getHeight()), true);
		        } else {
		        	gc.setTransform(transform);
		        	gc.drawString(string, xOffset + (i * fontMetrics.getHeight()), 2, true);
		        }
		    }
		    if (transform != null)
		    	transform.dispose();

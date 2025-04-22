		Control ctrl = (Control) pscModel.getWidget();
		Control[] ca = { ctrl };
		if (ctrl instanceof Shell)
			((Shell) ctrl).layout(ca);
		else
			ctrl.getParent().layout(ca);

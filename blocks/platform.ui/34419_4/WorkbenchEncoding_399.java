package org.eclipse.ui;

import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.SubCoolBarManager;
import org.eclipse.ui.services.IServiceLocator;

public class SubActionBars2 extends SubActionBars implements IActionBars2 {
	private SubCoolBarManager coolBarMgr = null;

	public SubActionBars2(final IActionBars2 parent) {
		this(parent, parent.getServiceLocator());
	}

	public SubActionBars2(final IActionBars2 parent,
			final IServiceLocator serviceLocator) {
		super(parent, serviceLocator);
	}

	protected IActionBars2 getCastedParent() {
		return (IActionBars2) getParent();
	}

	protected SubCoolBarManager createSubCoolBarManager(ICoolBarManager parent) {
		return new SubCoolBarManager(parent);
	}

	@Override
	public ICoolBarManager getCoolBarManager() {
		if (coolBarMgr == null) {
			coolBarMgr = createSubCoolBarManager(getCastedParent()
					.getCoolBarManager());
			coolBarMgr.setVisible(getActive());
		}
		return coolBarMgr;
	}

	@Override
	protected void setActive(boolean value) {
		super.setActive(value);
		if (coolBarMgr != null) {
			coolBarMgr.setVisible(value);
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		if (coolBarMgr != null) {
			coolBarMgr.removeAll();
		}
	}
}

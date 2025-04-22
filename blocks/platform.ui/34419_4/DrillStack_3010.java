package org.eclipse.ui.part;

import java.util.List;

    Object fElement;

    Object fPropertyName;

    List fExpansion = null;

    public DrillFrame(Object oElement, Object strPropertyName, List vExpansion) {
        fElement = oElement;
        fPropertyName = strPropertyName;
        fExpansion = vExpansion;
    }

    @Override
	public boolean equals(Object obj) {
        if (this == obj) {
			return true;
		}

        if (!(obj instanceof DrillFrame)) {
			return false;
		}

        DrillFrame oOther = (DrillFrame) obj;
        return ((fElement == oOther.fElement) && (fPropertyName
                .equals(oOther.fPropertyName)));
    }

    public Object getElement() {
        return fElement;
    }

    public List getExpansion() {
        return fExpansion;
    }

    public Object getPropertyName() {
        return fPropertyName;
    }
}

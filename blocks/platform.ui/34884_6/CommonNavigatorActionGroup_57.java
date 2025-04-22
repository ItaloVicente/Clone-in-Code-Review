package org.eclipse.ui.internal.navigator;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.PlatformObject;

public class AdaptabilityUtility {

	public static Object getAdapter(Object anElement, Class anAdapterType) {
		Assert.isNotNull(anAdapterType);
        if (anElement == null) {
            return null;
        }
        if (anAdapterType.isInstance(anElement)) {
            return anElement;
        }

        if (anElement instanceof IAdaptable) {
            IAdaptable adaptable = (IAdaptable) anElement;

            Object result = adaptable.getAdapter(anAdapterType);
            if (result != null) {
                Assert.isTrue(anAdapterType.isInstance(result));
                return result;
            }
        } 
        
        if (!(anElement instanceof PlatformObject)) {
            Object result = Platform.getAdapterManager().getAdapter(anElement, anAdapterType);
            if (result != null) {
                return result;
            }
        }

        return null;
	}

}

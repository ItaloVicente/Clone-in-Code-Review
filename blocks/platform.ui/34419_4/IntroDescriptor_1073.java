package org.eclipse.ui.internal.intro;

public interface IIntroRegistry {

    int getIntroCount();

    IIntroDescriptor[] getIntros();

    IIntroDescriptor getIntroForProduct(String productId);

    IIntroDescriptor getIntro(String id);
}

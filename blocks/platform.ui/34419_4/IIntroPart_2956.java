package org.eclipse.ui.intro;

import org.eclipse.ui.IWorkbenchWindow;

public interface IIntroManager {

    public boolean closeIntro(IIntroPart part);

    public IIntroPart getIntro();

    public boolean hasIntro();

    boolean isIntroStandby(IIntroPart part);

    public void setIntroStandby(IIntroPart part, boolean standby);

    public IIntroPart showIntro(IWorkbenchWindow preferredWindow,
            boolean standby);
    
    public boolean isNewContentAvailable();
}

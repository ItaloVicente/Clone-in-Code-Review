package org.eclipse.ui;


public interface ISizeProvider {

    public static final int INFINITE = Integer.MAX_VALUE;
    
    public int getSizeFlags(boolean width);
    
    public int computePreferredSize(boolean width, int availableParallel, int availablePerpendicular, int preferredResult);
}

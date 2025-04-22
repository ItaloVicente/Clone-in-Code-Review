package org.eclipse.ui.internal.preferences;

public abstract class AbstractIntegerListener extends AbstractPropertyListener {

    private IDynamicPropertyMap map;
    private int defaultValue;
    private String propertyId;
    
    public AbstractIntegerListener() {
    }
    
    public void attach(IDynamicPropertyMap map, String propertyId, int defaultValue) {
        this.defaultValue = defaultValue;
        this.propertyId = propertyId;
        if (this.map != null) {
            this.map.removeListener(this);
        }
        
        this.map = map;
        
        if (this.map != null) {
            this.map.addListener(new String[]{propertyId}, this);
        }
    }

    @Override
	protected void update() {
        handleValue(PropertyUtil.get(map, propertyId, defaultValue));
    }

    protected abstract void handleValue(int b);
   
    
}

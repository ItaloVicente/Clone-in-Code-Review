package org.eclipse.ui.internal.preferences;

public abstract class AbstractBooleanListener extends AbstractPropertyListener {

    private IDynamicPropertyMap map;
    private boolean defaultValue;
    private String propertyId;
    
    public AbstractBooleanListener() {
    }
    
    public void attach(IDynamicPropertyMap map, String propertyId, boolean defaultValue) {
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

    protected abstract void handleValue(boolean b);
   
    
}

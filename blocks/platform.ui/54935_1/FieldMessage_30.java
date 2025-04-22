
package org.eclipse.ui.views.markers.internal;

import org.eclipse.core.resources.IMarker;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.model.IWorkbenchAdapter;

public class FieldLineNumber extends AbstractField {

    private String description;

    private Image image;

    public FieldLineNumber() {
        description = MarkerMessages.description_lineNumber;
    }

    @Override
	public String getDescription() {
        return description;
    }

    @Override
	public Image getDescriptionImage() {
        return image;
    }

    @Override
	public String getColumnHeaderText() {
        return description;
    }

    @Override
	public Image getColumnHeaderImage() {
        return image;
    }

    @Override
	public String getValue(Object obj) {
    	if (obj == null) {
			return MarkerMessages.FieldMessage_NullMessage;
		}

    	if (obj instanceof MarkerNode){
			MarkerNode node = (MarkerNode) obj;
	    	if(node.isConcrete()){
	    		ConcreteMarker concreteNode = (ConcreteMarker) node;
	    		if(concreteNode.getLocationString().length() == 0){
	    			if (concreteNode.getLine() < 0) {
						return MarkerMessages.Unknown;
					}	    	   
	    	        return NLS.bind(
	    	        		MarkerMessages.label_lineNumber,
	    	        		Integer.toString(concreteNode.getLine()));
	    		}
	    		return concreteNode.getLocationString();
	    	}
	    	return Util.EMPTY_STRING;
		}
		
		if(obj instanceof IWorkbenchAdapter) {
			return Util.EMPTY_STRING;//Don't show pending
		}
		
		if(obj instanceof IMarker) {
			return Util.getProperty(IMarker.LINE_NUMBER, (IMarker) obj);
		} 
		
		return NLS.bind(MarkerMessages.FieldMessage_WrongType,obj.toString());
        
    }

    @Override
	public Image getImage(Object obj) {
        return null;
    }

    @Override
	public int compare(Object obj1, Object obj2) {
        if (obj1 == null || obj2 == null || !(obj1 instanceof ConcreteMarker)
                || !(obj2 instanceof ConcreteMarker)) {
            return 0;
        }

        ConcreteMarker marker1 = (ConcreteMarker) obj1;
        ConcreteMarker marker2 = (ConcreteMarker) obj2;
        
        String location1 = marker1.getLocationString();
        String location2 = marker2.getLocationString();
        
        if(location1.length() == 0 || location2.length() == 0) {
			return marker1.getLine() - marker2.getLine();
		}
        
        return location1.compareTo(location2);
    }

	@Override
	public int getDefaultDirection() {
		return TableComparator.ASCENDING;
	}
	
	@Override
	public int getPreferredWidth() {
		return 60;
	}
}

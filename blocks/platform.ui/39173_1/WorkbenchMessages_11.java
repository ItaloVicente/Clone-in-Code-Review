package org.eclipse.e4.ui.internal;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;

import org.eclipse.core.runtime.IProduct;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.ui.branding.IProductConstants;
import org.eclipse.jface.resource.ImageDescriptor;
import org.osgi.framework.Bundle;

import com.ibm.icu.text.MessageFormat;

public class ProductProperties extends BrandingProperties implements
        IProductConstants {

    private final IProduct product;

    private String appName;

    private String aboutText;

    private ImageDescriptor aboutImageDescriptor;

    private ImageDescriptor[] windowImageDescriptors;

    private URL welcomePageUrl;

    private String productName;

    private String productId;

    private static final String ABOUT_MAPPINGS = "$nl$/about.mappings"; //$NON-NLS-1$

    private static HashMap mappingsMap = new HashMap(4);
    
    private static String[] loadMappings(Bundle definingBundle) {
        URL location = Platform.find(definingBundle, new Path(
                ABOUT_MAPPINGS));
        PropertyResourceBundle bundle = null;
        InputStream is;
        if (location != null) {
            is = null;
            try {
                is = location.openStream();
                bundle = new PropertyResourceBundle(is);
            } catch (IOException e) {
                bundle = null;
            } finally {
                try {
                    if (is != null) {
						is.close();
					}
                } catch (IOException e) {
                }
            }
        }

        ArrayList mappingsList = new ArrayList();
        if (bundle != null) {
            boolean found = true;
            int i = 0;
            while (found) {
                try {
                    mappingsList.add(bundle.getString(Integer.toString(i)));
                } catch (MissingResourceException e) {
                    found = false;
                }
                i++;
            }
        }
        String[] mappings = (String[]) mappingsList.toArray(new String[mappingsList.size()]);
        mappingsMap.put(definingBundle, mappings);
        return mappings;
    }
    
    private static String[] getMappings(Bundle definingBundle) {
    	String[] mappings = (String[]) mappingsMap.get(definingBundle);
    	if (mappings == null) {
    		mappings = loadMappings(definingBundle);
    	}
    	if (mappings == null) {
    		mappings = new String[0];
    	}
    	return mappings;
    }
    
    public ProductProperties(IProduct product) {
        if (product == null) {
			throw new IllegalArgumentException();
		}
        this.product = product;
    }

    public String getAppName() {
        if (appName == null) {
			appName = getAppName(product);
		}
        return appName;
    }

    public String getAboutText() {
        if (aboutText == null) {
			aboutText = getAboutText(product);
		}
        return aboutText;
    }

    public ImageDescriptor getAboutImage() {
        if (aboutImageDescriptor == null) {
			aboutImageDescriptor = getAboutImage(product);
		}
        return aboutImageDescriptor;
    }

    public ImageDescriptor[] getWindowImages() {
        if (windowImageDescriptors == null) {
			windowImageDescriptors = getWindowImages(product);
		}
        return windowImageDescriptors;
    }

    public URL getWelcomePageUrl() {
        if (welcomePageUrl == null) {
			welcomePageUrl = getWelcomePageUrl(product);
		}
        return welcomePageUrl;
    }

    public String getProductName() {
        if (productName == null) {
			productName = getProductName(product);
		}
        return productName;
    }

    public String getProductId() {
        if (productId == null) {
			productId = getProductId(product);
		}
        return productId;
    }

    public static String getAppName(IProduct product) {
        String property = product.getProperty(APP_NAME);
        if (property == null) {
			return ""; //$NON-NLS-1$
		}
        if (property.indexOf('{') == -1) {
			return property;
		}
        String[] mappings = getMappings(product.getDefiningBundle());
		return MessageFormat.format(property, (Object[]) mappings);
    }

    public static String getAboutText(IProduct product) {
        String property = product.getProperty(ABOUT_TEXT);
        if (property == null) {
			return ""; //$NON-NLS-1$
		}
        if (property.indexOf('{') == -1) {
			return property;
		}
        String[] tempMappings = getMappings(product.getDefiningBundle());
        for (int i=0; i<tempMappings.length; i++) {
        	String nextString = tempMappings[i];
        	int length = nextString.length();
        	
        	if (length > 2 && nextString.charAt(0) == '$' && nextString.charAt(length-1) == '$') {
        		String systemPropertyKey = nextString.substring(1, length-1);
        		tempMappings[i] = System.getProperty(systemPropertyKey, ""); //$NON-NLS-1$;
        	}
        }

		return MessageFormat.format(property, (Object[]) tempMappings);
    }

    public static ImageDescriptor getAboutImage(IProduct product) {
        return getImage(product.getProperty(ABOUT_IMAGE), product
                .getDefiningBundle());
    }

    public static ImageDescriptor[] getWindowImages(IProduct product) {
        String property = product.getProperty(WINDOW_IMAGES);

        if (property == null) {
			property = product.getProperty(WINDOW_IMAGE);
		}

        return getImages(property, product.getDefiningBundle());
    }

    public static URL getWelcomePageUrl(IProduct product) {
        return getUrl(product.getProperty(WELCOME_PAGE), product
                .getDefiningBundle());
    }

    public static String getProductName(IProduct product) {
        return product.getName();
    }

    public static String getProductId(IProduct product) {
        return product.getId();
    }
}

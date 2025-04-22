package org.eclipse.e4.ui.about.dialog;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IBundleGroup;
import org.eclipse.core.runtime.IBundleGroupProvider;
import org.eclipse.core.runtime.IProduct;
import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.ui.internal.ProductProperties;
import org.eclipse.e4.ui.internal.WorkbenchMessages;
import org.eclipse.e4.ui.internal.about.AboutBundleGroupData;
import org.eclipse.e4.ui.internal.about.AboutFeaturesButtonManager;
import org.eclipse.e4.ui.internal.about.AboutItem;
import org.eclipse.e4.ui.internal.about.AboutTextManager;
import org.eclipse.e4.ui.internal.about.InstallationDialog;
import org.eclipse.e4.ui.internal.dialog.AboutFeaturesDialog;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceColors;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.accessibility.AccessibleAdapter;
import org.eclipse.swt.accessibility.AccessibleEvent;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class AboutDialogE4 extends TrayDialog {
    private final static int MAX_IMAGE_WIDTH_FOR_TEXT = 250;

    private final static int DETAILS_ID = IDialogConstants.CLIENT_ID + 1;

    private String productName;

    private IProduct product;

    private AboutBundleGroupData[] bundleGroupInfos;

    private List<Image> images = new ArrayList<Image>();

    private AboutFeaturesButtonManager buttonManager = new AboutFeaturesButtonManager();

    private StyledText text;
    
    private AboutTextManager aboutTextManager;

    public AboutDialogE4(Shell parentShell) {
        super(parentShell);

        product = Platform.getProduct();
        if (product != null) {
			productName = product.getName();
		}
        if (productName == null) {
			productName = WorkbenchMessages.AboutDialog_defaultProductName;
		}

        IBundleGroupProvider[] providers = Platform.getBundleGroupProviders();
        LinkedList<AboutBundleGroupData> groups = new LinkedList<AboutBundleGroupData>();
        if (providers != null) {
			for (int i = 0; i < providers.length; ++i) {
                IBundleGroup[] bundleGroups = providers[i].getBundleGroups();
                for (int j = 0; j < bundleGroups.length; ++j) {
					groups.add(new AboutBundleGroupData(bundleGroups[j]));
				}
            }
		}
        bundleGroupInfos = (AboutBundleGroupData[]) groups
                .toArray(new AboutBundleGroupData[0]);
    }

    @Override
	protected void buttonPressed(int buttonId) {
        switch (buttonId) {
        case DETAILS_ID:
			BusyIndicator.showWhile(getShell().getDisplay(), new Runnable() {
				@Override
				public void run() {
					InstallationDialog dialog = new InstallationDialog(getShell());
					dialog.setModalParent(AboutDialogE4.this);
					dialog.open();	
				}
			});
            break;
        default:
            super.buttonPressed(buttonId);
            break;
        }
    }

    @Override
	public boolean close() {
        for (int i = 0; i < images.size(); ++i) {
            Image image = images.get(i);
            image.dispose();
        }

        return super.close();
    }

    @Override
	protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText(NLS.bind(WorkbenchMessages.AboutDialog_shellTitle,productName ));
        
    }

    @Override
	protected void createButtonsForButtonBar(Composite parent) {
        parent.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        createButton(parent, DETAILS_ID, WorkbenchMessages.AboutDialog_DetailsButton, false); 

        Label l = new Label(parent, SWT.NONE);
        l.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        GridLayout layout = (GridLayout) parent.getLayout();
        layout.numColumns++;
        layout.makeColumnsEqualWidth = false;

        Button b = createButton(parent, IDialogConstants.OK_ID,
                IDialogConstants.OK_LABEL, true);
        b.setFocus();
    }

    @Override
	protected Control createDialogArea(Composite parent) {
        Image aboutImage = null;
        AboutItem item = null;
        if (product != null) {
            ImageDescriptor imageDescriptor = ProductProperties
                    .getAboutImage(product);
            if (imageDescriptor != null) {
				aboutImage = imageDescriptor.createImage();
			}

            if (aboutImage == null
                    || aboutImage.getBounds().width <= MAX_IMAGE_WIDTH_FOR_TEXT) {
                String aboutText = ProductProperties.getAboutText(product);
                if (aboutText != null) {
					item = AboutTextManager.scan(aboutText);
				}
            }

            if (aboutImage != null) {
				images.add(aboutImage);
			}
        }

        Composite workArea = new Composite(parent, SWT.NONE);
        GridLayout workLayout = new GridLayout();
        workLayout.marginHeight = 0;
        workLayout.marginWidth = 0;
        workLayout.verticalSpacing = 0;
        workLayout.horizontalSpacing = 0;
        workArea.setLayout(workLayout);
        workArea.setLayoutData(new GridData(GridData.FILL_BOTH));

        Color background = JFaceColors.getBannerBackground(parent.getDisplay());
        Color foreground = JFaceColors.getBannerForeground(parent.getDisplay());
        Composite top = (Composite) super.createDialogArea(workArea);

        GridLayout layout = new GridLayout();
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        layout.verticalSpacing = 0;
        layout.horizontalSpacing = 0;
        top.setLayout(layout);
        top.setLayoutData(new GridData(GridData.FILL_BOTH));
        top.setBackground(background);
        top.setForeground(foreground);

        final Composite topContainer = new Composite(top, SWT.NONE);
        topContainer.setBackground(background);
        topContainer.setForeground(foreground);

        layout = new GridLayout();
        layout.numColumns = (aboutImage == null || item == null ? 1 : 2);
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        layout.verticalSpacing = 0;
        layout.horizontalSpacing = 0;
        topContainer.setLayout(layout);
        

        GC gc = new GC(parent);
        int topContainerHeightHint = 100;
        try {
			topContainerHeightHint = Math.max(topContainerHeightHint, gc
					.getFontMetrics().getHeight() * 6);
        }
        finally {
        	gc.dispose();
        }
        
        if (aboutImage != null) {
            Label imageLabel = new Label(topContainer, SWT.NONE);
            imageLabel.setBackground(background);
            imageLabel.setForeground(foreground);

            GridData data = new GridData();
            data.horizontalAlignment = GridData.FILL;
            data.verticalAlignment = GridData.BEGINNING;
            data.grabExcessHorizontalSpace = false;
            imageLabel.setLayoutData(data);
            imageLabel.setImage(aboutImage);
            topContainerHeightHint = Math.max(topContainerHeightHint, aboutImage.getBounds().height);
        }
        
        GridData data = new GridData();
        data.horizontalAlignment = GridData.FILL;
        data.verticalAlignment = GridData.FILL;
        data.grabExcessHorizontalSpace = true;
        data.grabExcessVerticalSpace = true;
        data.heightHint = topContainerHeightHint;
        topContainer.setLayoutData(data);
        
        if (item != null) {
			final int minWidth = 400; // This value should really be calculated
        	final ScrolledComposite scroller = new ScrolledComposite(topContainer,
    				SWT.V_SCROLL | SWT.H_SCROLL);
        	data = new GridData(GridData.FILL_BOTH);
        	data.widthHint = minWidth;
    		scroller.setLayoutData(data);

    		final Composite textComposite = new Composite(scroller, SWT.NONE);
    		textComposite.setBackground(background);
    		
    		layout = new GridLayout();
    		textComposite.setLayout(layout);

    		text = new StyledText(textComposite, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY);


            text.setFont(parent.getFont());
            text.setText(item.getText());
            text.setCursor(null);
            text.setBackground(background);
            text.setForeground(foreground);
            
            aboutTextManager = new AboutTextManager(text);
            aboutTextManager.setItem(item);
            
            createTextMenu();
            
    		GridData gd = new GridData();
    		gd.verticalAlignment = GridData.BEGINNING;
    		gd.horizontalAlignment = GridData.FILL;
    		gd.grabExcessHorizontalSpace = true;
    		text.setLayoutData(gd);

    		scroller.getHorizontalBar().setIncrement(20);
    		scroller.getVerticalBar().setIncrement(20);

    		final boolean[] inresize = new boolean[1]; // flag to stop unneccesary
    		textComposite.addControlListener(new ControlAdapter() {
    			@Override
				public void controlResized(ControlEvent e) {
    				if (inresize[0])
    					return;
    				inresize[0] = true;
    				textComposite.layout(true);
    				int width = textComposite.getClientArea().width;
    				Point p = textComposite.computeSize(width, SWT.DEFAULT);
    				scroller.setMinSize(minWidth, p.y);
    				inresize[0] = false;
    			}
    		});

    		scroller.setExpandHorizontal(true);
    		scroller.setExpandVertical(true);
    		Point p = textComposite.computeSize(minWidth, SWT.DEFAULT);
    		textComposite.setSize(p.x, p.y);
    		scroller.setMinWidth(minWidth);
    		scroller.setMinHeight(p.y);

    		scroller.setContent(textComposite);
        }

        Label bar = new Label(workArea, SWT.HORIZONTAL | SWT.SEPARATOR);
        data = new GridData();
        data.horizontalAlignment = GridData.FILL;
        bar.setLayoutData(data);

        Composite bottom = (Composite) super.createDialogArea(workArea);
        layout = new GridLayout();
        bottom.setLayout(layout);
        data = new GridData();
        data.horizontalAlignment = SWT.FILL;
        data.verticalAlignment = SWT.FILL;
        data.grabExcessHorizontalSpace = true;
        
        bottom.setLayoutData(data);

        createFeatureImageButtonRow(bottom);

        bar = new Label(bottom, SWT.NONE);
        data = new GridData();
        data.horizontalAlignment = GridData.FILL;
        bar.setLayoutData(data);

        return workArea;
    }

	private void createTextMenu() {
		final MenuManager textManager = new MenuManager();	
		
		
		text.setMenu(textManager.createContextMenu(text));
		text.addDisposeListener(new DisposeListener() {

			@Override
			public void widgetDisposed(DisposeEvent e) {
				textManager.dispose();
			}
		});
		
	}

	private void createFeatureImageButtonRow(Composite parent) {
        Composite featureContainer = new Composite(parent, SWT.NONE);
        RowLayout rowLayout = new RowLayout();
        rowLayout.wrap = true;
        featureContainer.setLayout(rowLayout);
        GridData data = new GridData();
        data.horizontalAlignment = GridData.FILL;
        featureContainer.setLayoutData(data);

        for (int i = 0; i < bundleGroupInfos.length; i++) {
			createFeatureButton(featureContainer, bundleGroupInfos[i]);
		}
    }

    private Button createFeatureButton(Composite parent,
            final AboutBundleGroupData info) {
        if (!buttonManager.add(info)) {
			return null;
		}

        ImageDescriptor desc = info.getFeatureImage();
        Image featureImage = null;

        Button button = new Button(parent, SWT.FLAT | SWT.PUSH);
        button.setData(info);
        featureImage = desc.createImage();
        images.add(featureImage);
        button.setImage(featureImage);
        button.setToolTipText(info.getProviderName());
        
        button.getAccessible().addAccessibleListener(new AccessibleAdapter(){
			@Override
			public void getName(AccessibleEvent e) {
				e.result = info.getProviderName();
			}
        });
        button.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent event) {
                AboutBundleGroupData[] groupInfos = buttonManager
                        .getRelatedInfos(info);
                AboutBundleGroupData selection = (AboutBundleGroupData) event.widget
                        .getData();

                AboutFeaturesDialog d = new AboutFeaturesDialog(getShell(),
                        productName, groupInfos, selection);
                d.open();
            }
        });

        return button;
    }

	@Override
	protected boolean isResizable() {
		return true;
	}
}

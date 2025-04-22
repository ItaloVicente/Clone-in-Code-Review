/*******************************************************************************
 * (c) Copyright 2015 l33t labs LLC and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     l33t labs LLC and others - initial contribution
 *******************************************************************************/

package org.eclipse.ui.images.renderer;

import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import javax.imageio.ImageIO;

import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.gvt.renderer.ImageRenderer;
import org.apache.batik.transcoder.ErrorHandler;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.batik.util.XMLResourceDescriptor;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGDocument;

import com.jhlabs.image.ContrastFilter;
import com.jhlabs.image.GrayscaleFilter;
import com.jhlabs.image.HSBAdjustFilter;

/**
 * <p>Mojo which renders SVG icons into PNG format.</p>
 *
 * @goal render-icons
 * @phase generate-resources
 */
public class RenderMojo extends AbstractMojo {

    /** Maven logger */
    Log log;

    /** Used for high res rendering support. */
    public static final String ECLIPSE_SVG_SCALE = "eclipse.svg.scale";

    /** Used to specify the number of render threads when rasterizing icons. */
    public static final String RENDERTHREADS = "eclipse.svg.renderthreads";

    /** Used to specify the directory name where the SVGs are taken from. */
    public static final String SOURCE_DIR = "eclipse.svg.sourcedirectory";

    /** Used to specify the directory name where the PNGs are saved to. */
    public static final String TARGET_DIR = "eclipse.svg.targetdirectory";

    /** A list of directories with svg sources to rasterize. */
    private List<IconEntry> icons;

    /** The pool used to render multiple icons concurrently. */
    private ExecutorService execPool;

    /** The number of threads to use when rendering icons. */
    private int threads;

    /**
     * A counter used to keep track of the number of rendered icons. Atomic is
     * used to make it easy to access between threads concurrently.
     */
    private AtomicInteger counter;

    /** List of icons that failed to render, made safe for parallel access */
    List<IconEntry> failedIcons = Collections
            .synchronizedList(new ArrayList<IconEntry>(5));

    /** The amount of scaling to apply to rasterized images. */
    private double outputScale;

    /**
     * @return the number of icons rendered at the time of the call
     */
    public int getIconsRendered() {
        return counter.get();
    }

    /**
     * @return the number of icons that failed during the rendering process
     */
    public int getFailedIcons() {
        return failedIcons.size();
    }

    /**
     * <p>Generates raster images from the input SVG vector image.</p>
     *
     * @param icon
     *            the icon to render
     */
    public void rasterize(IconEntry icon, GrayscaleFilter grayFilter, HSBAdjustFilter desaturator, ContrastFilter decontrast) {
        if (icon == null) {
            log.error("Null icon definition, skipping.");
            failedIcons.add(icon);
            return;
        }

        if (icon.inputPath == null) {
            log.error("Null icon input path, skipping: "
                    + icon.nameBase);
            failedIcons.add(icon);
            return;
        }

        if (!icon.inputPath.exists()) {
            log.error("Input path specified does not exist, skipping: "
                            + icon.nameBase);
            failedIcons.add(icon);
            return;
        }

        if (icon.outputPath != null && !icon.outputPath.exists()) {
            icon.outputPath.mkdirs();
        }

        if (icon.disabledPath != null && !icon.disabledPath.exists()) {
            icon.disabledPath.mkdirs();
        }

        SVGDocument svgDocument = generateSVGDocument(icon);

        if(svgDocument == null) {
            return;
        }

        Element svgDocumentNode = svgDocument.getDocumentElement();
        String nativeWidthStr = svgDocumentNode.getAttribute("width");
        String nativeHeightStr = svgDocumentNode.getAttribute("height");
        int nativeWidth = -1;
        int nativeHeight = -1;

        try{
            if (nativeWidthStr != "" && nativeHeightStr != ""){
                nativeWidth = Integer.parseInt(nativeWidthStr);
                nativeHeight = Integer.parseInt(nativeHeightStr);
            } else {
                String viewBoxStr = svgDocumentNode.getAttribute("viewBox");
                if (viewBoxStr == ""){
                    log.error("Icon defines neither width/height nor a viewBox, skipping: " + icon.nameBase);
                    failedIcons.add(icon);
                    return;
                }
                String[] splitted = viewBoxStr.split(" ");
                String xStr = splitted[0];
                String yStr = splitted[1];
                String widthStr = splitted[2];
                String heightStr = splitted[3];
                nativeWidth = Integer.parseInt(widthStr) - Integer.parseInt(xStr);
                nativeHeight = Integer.parseInt(heightStr) - Integer.parseInt(yStr); 
            }
        }catch (NumberFormatException e){
            log.error("Dimension could not be parsed ( "+e.getMessage()+ "), skipping: " + icon.nameBase);
            failedIcons.add(icon);
            return;
        }

        int outputWidth = (int) (nativeWidth * outputScale);
        int outputHeight = (int) (nativeHeight * outputScale);

        int outputInitSize = nativeWidth * nativeHeight * 4 + 1024;
        ByteArrayOutputStream iconOutput = new ByteArrayOutputStream(
                outputInitSize);

        try {
            log.info(Thread.currentThread().getName() + " "
                    + " Rasterizing: " + icon.nameBase + ".png at " + outputWidth
                    + "x" + outputHeight);

            TranscoderInput svgInput = new TranscoderInput(svgDocument);

            boolean success = renderIcon(icon.nameBase, outputWidth, outputHeight, svgInput, iconOutput);

            if (!success) {
                log.error("Failed to render icon: " + icon.nameBase + ".png, skipping.");
                failedIcons.add(icon);
                return;
            }
        } catch (Exception e) {
            log.error("Failed to render icon: " + e.getMessage());
            failedIcons.add(icon);
            return;
        }

        byte[] imageBytes = iconOutput.toByteArray();
        ByteArrayInputStream imageInputStream = new ByteArrayInputStream(imageBytes);

        BufferedImage inputImage = null;
        try {
            inputImage = ImageIO.read(imageInputStream);

            if(inputImage == null) {
                log.error("Failed to generate BufferedImage from rendered icon, ImageIO returned null: " + icon.nameBase);
                failedIcons.add(icon);
                return;
            }
        } catch (IOException e2) {
            log.error("Failed to generate BufferedImage from rendered icon: "  + icon.nameBase + " - " + e2.getMessage());
            failedIcons.add(icon);
            return;
        }

        writeIcon(icon, outputWidth, outputHeight, inputImage);

        try {
            if (icon.disabledPath != null) {
                BufferedImage desaturated16 = desaturator.filter(
                    grayFilter.filter(inputImage, null), null);

                BufferedImage deconstrast = decontrast.filter(desaturated16, null);

                ImageIO.write(deconstrast, "PNG", new File(icon.disabledPath, icon.nameBase + ".png"));
            }
        } catch (Exception e1) {
            log.error("Failed to render disabled icon: "  +
                               icon.nameBase, e1);
            failedIcons.add(icon);
        }
    }

    /**
     * <p>Generates a Batik SVGDocument for the supplied IconEntry's input
     * file.</p>
     *
     * @param icon the icon entry to generate an SVG document for
     *
     * @return a batik SVGDocument instance or null if one could not be generated
     */
    private SVGDocument generateSVGDocument(IconEntry icon) {
        SVGDocument svgDocument = null;
        try {
            FileInputStream iconDocumentStream = new FileInputStream(icon.inputPath);

            String parser = XMLResourceDescriptor.getXMLParserClassName();
            SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);

        } catch (Exception e3) {
            log.error("Error parsing SVG icon document: " + e3.getMessage());
            failedIcons.add(icon);
            return null;
        }
        return svgDocument;
    }

    /**
     * <p>Resizes the supplied inputImage to the specified width and height, using
     * lanczos resampling techniques.</p>
     *
     * @param icon the icon that's being resized
     * @param width the desired output width after rescaling operations
     * @param height the desired output height after rescaling operations
     * @param sourceImage the source image to resource
     */
    private void writeIcon(IconEntry icon, int width, int height, BufferedImage sourceImage) {
        try {
            String outputName = icon.nameBase;
            if (outputScale != 1) {
                String scaleId = outputScale == (double) (int) outputScale ? Integer.toString((int) outputScale): Double.toString(outputScale);
                outputName += "@" + scaleId + "x";
            }
            outputName += ".png";
            ImageIO.write(sourceImage, "PNG", new File(icon.outputPath, outputName));
        } catch (Exception e1) {
            log.error("Failed to resize rendered icon to output size: "  +
                               icon.nameBase, e1);
            failedIcons.add(icon);
        }
    }

    /**
     * <p>Handles concurrently rasterizing the icons to
     * reduce the duration on multicore systems.</p>
     */
    public void rasterizeAll() {
        int remainingIcons = icons.size();

        final int threadExecSize = Math.max(1, icons.size() / this.threads);

        int batchOffset = 0;

        List<Callable<Object>> tasks = new ArrayList<>(
                this.threads);

        while (remainingIcons > 0) {
            final int batchStart = batchOffset;

            batchOffset += threadExecSize;

            int batchSize = 0;

            if (remainingIcons > threadExecSize) {
                batchSize = threadExecSize;
            } else {
                batchSize = remainingIcons;
            }

            remainingIcons -= threadExecSize;

            final int execCount = batchSize;

            Callable<Object> runnable = new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    GrayscaleFilter grayFilter = new GrayscaleFilter();

                    HSBAdjustFilter desaturator = new HSBAdjustFilter();
                         desaturator.setSFactor(0.0f);

                    ContrastFilter decontrast = new ContrastFilter();
                         decontrast.setBrightness(2.9f);
                         decontrast.setContrast(0.2f);

                    for (int count = 0; count < execCount; count++) {
                        rasterize(icons.get(batchStart + count), grayFilter, desaturator, decontrast);
                    }

                    counter.getAndAdd(execCount);
                    log.info("Finished rendering batch, index: " + batchStart);

                    return null;
                }
            };

            tasks.add(runnable);
        }

        try {
            execPool.invokeAll(tasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("Failed Icon Count: " + failedIcons.size());
        for (IconEntry icon : failedIcons) {
            log.info("Failed Icon: " + icon.nameBase);
        }

    }

    /**
     * Use batik to rasterize the input SVG into a raster image at the specified
     * image dimensions.
     *
     * @param width the width to render the icons at
     * @param height the height to render the icon at
     * @param input the SVG transcoder input
     * @param stream the stream to write the PNG data to
     */
    public boolean renderIcon(final String iconName, int width, int height,
            TranscoderInput tinput, OutputStream stream) {
        PNGTranscoder transcoder = new PNGTranscoder() {
            protected ImageRenderer createRenderer() {
                ImageRenderer renderer = super.createRenderer();

                RenderingHints renderHints = renderer.getRenderingHints();

                renderHints.add(new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_OFF));

                renderHints.add(new RenderingHints(RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY));

                renderHints.add(new RenderingHints(RenderingHints.KEY_DITHERING,
                    RenderingHints.VALUE_DITHER_DISABLE));

                renderHints.add(new RenderingHints(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BICUBIC));

                renderHints.add(new RenderingHints(RenderingHints.KEY_ALPHA_INTERPOLATION,
                    RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY));

                renderHints.add(new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON));

                renderHints.add(new RenderingHints(RenderingHints.KEY_COLOR_RENDERING,
                    RenderingHints.VALUE_COLOR_RENDER_QUALITY));

                renderHints.add(new RenderingHints(RenderingHints.KEY_STROKE_CONTROL,
                    RenderingHints.VALUE_STROKE_PURE));

                renderHints.add(new RenderingHints(RenderingHints.KEY_FRACTIONALMETRICS,
                    RenderingHints.VALUE_FRACTIONALMETRICS_ON));

                renderer.setRenderingHints(renderHints);

                return renderer;
            }
        };

        transcoder.addTranscodingHint(PNGTranscoder.KEY_WIDTH, new Float(width));
        transcoder.addTranscodingHint(PNGTranscoder.KEY_HEIGHT, new Float(height));

        transcoder.setErrorHandler(new ErrorHandler() {
            public void warning(TranscoderException arg0)
                    throws TranscoderException {
                log.error("Icon: " + iconName + " - WARN: " + arg0.getMessage());
            }

            public void fatalError(TranscoderException arg0)
                    throws TranscoderException {
                log.error("Icon: " + iconName + " - FATAL: " + arg0.getMessage());
            }

            public void error(TranscoderException arg0)
                    throws TranscoderException {
                log.error("Icon: " + iconName + " - ERROR: " + arg0.getMessage());
            }
        });

        TranscoderOutput output = new TranscoderOutput(stream);

        try {
            transcoder.transcode(tinput, output);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * <p>Initializes rasterizer defaults</p>
     *
     * @param threads the number of threads to render with
     * @param scale multiplier to use with icon output dimensions
     */
    private void init(int threads, double scale) {
        this.threads = threads;
        this.outputScale = Math.max(1, scale);
        icons = new ArrayList<>();
        execPool = Executors.newFixedThreadPool(threads);
        counter = new AtomicInteger();
    }

    /**
     * @see AbstractMojo#execute()
     */
    public void execute() throws MojoExecutionException, MojoFailureException {
        log = getLog();

        int threads = Math.max(1, Runtime.getRuntime().availableProcessors() * 2);
        String threadStr = System.getProperty(RENDERTHREADS);
        if (threadStr != null) {
            try {
                threads = Integer.parseInt(threadStr);
            } catch (Exception e) {
                e.printStackTrace();
                System.out
                        .println("Could not parse thread count, using default thread count");
            }
        }

        double iconScale = 1;
        String iconScaleStr = System.getProperty(ECLIPSE_SVG_SCALE);
        if (iconScaleStr != null) {
            iconScale = Double.parseDouble(iconScaleStr);
            if (iconScale != 1 && iconScale != 1.5 && iconScale != 2) {
                log.warn("Unusual scale factor: " + iconScaleStr + " (@" + iconScale + "x)");
            }
        }

        String sourceDir = "eclipse-svg";
        String sourceDirProp = System.getProperty(SOURCE_DIR);
        if (sourceDirProp != null) {
            sourceDir = sourceDirProp;
        }

        String targetDir = "eclipse-png";
        String targetDirProp = System.getProperty(TARGET_DIR);
        if (targetDirProp != null) {
            targetDir = targetDirProp;
        }

        long totalStartTime = System.currentTimeMillis();

        init(threads, iconScale);

        String workingDirectory = System.getProperty("user.dir");

        File outputDir = new File(workingDirectory + (iconScale == 1 ? "/" + targetDir + "/" : "/" + targetDir + "-highdpi/"));
        File iconDirectoryRoot = new File(sourceDir + "/");

        for (File file : iconDirectoryRoot.listFiles()) {
            if(!file.isDirectory()) {
                continue;
            }

            String dirName = file.getName();

            File outputBase = new File(outputDir, (iconScale == 1 ? dirName : dirName + ".highdpi"));
            if (iconScale != 1) {
                createFragmentFiles(outputBase, dirName);
            }

            IconGatherer.gatherIcons(icons, "svg", file, file, outputBase, true);
        }

        log.info("Working directory: " + outputDir.getAbsolutePath());
        log.info("SVG Icon Directory: " + iconDirectoryRoot.getAbsolutePath());
        log.info("Rendering icons with " + threads + " threads, scaling output to " + iconScale + "x");
        long startTime = System.currentTimeMillis();

        rasterizeAll();

        int iconRendered = getIconsRendered();
        int failedIcons = getFailedIcons();
        int fullIconCount = iconRendered - failedIcons;

        log.info(fullIconCount + " Icons Rendered");
        log.info(failedIcons + " Icons Failed");
        log.info("Took: "    + (System.currentTimeMillis() - startTime) + " ms.");

        log.info("Rasterization operations completed, Took: "
                + (System.currentTimeMillis() - totalStartTime) + " ms.");
    }

    private void createFragmentFiles(File outputBase, String dirName) {
        createFile(new File(outputBase, "build.properties"), "bin.includes = META-INF/,icons/,.\n");
        createFile(new File(outputBase, ".project"), "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
                "<projectDescription>\n" + 
                "    <name>" + dirName + ".highdpi</name>\n" + 
                "    <comment></comment>\n" + 
                "    <projects>\n" + 
                "    </projects>\n" + 
                "    <buildSpec>\n" + 
                "        <buildCommand>\n" + 
                "            <name>org.eclipse.pde.ManifestBuilder</name>\n" + 
                "            <arguments>\n" + 
                "            </arguments>\n" + 
                "        </buildCommand>\n" + 
                "        <buildCommand>\n" + 
                "            <name>org.eclipse.pde.SchemaBuilder</name>\n" + 
                "            <arguments>\n" + 
                "            </arguments>\n" + 
                "        </buildCommand>\n" + 
                "    </buildSpec>\n" + 
                "    <natures>\n" + 
                "        <nature>org.eclipse.pde.PluginNature</nature>\n" + 
                "    </natures>\n" + 
                "</projectDescription>\n");
        createFile(new File(outputBase, "META-INF/MANIFEST.MF"), "Manifest-Version: 1.0\n" + 
                "Bundle-ManifestVersion: 2\n" + 
                "Bundle-Name: " + dirName + ".highdpi\n" + 
                "Bundle-SymbolicName: " + dirName + ".highdpi\n" + 
                "Bundle-Version: 0.1.0.qualifier\n" + 
                "Fragment-Host: " + dirName + "\n");
    }

    private void createFile(File file, String contents) {
        try {
            file.getParentFile().mkdirs();
            FileWriter writer = new FileWriter(file);
            writer.write(contents);
            writer.close();
        } catch (IOException e) {
            log.error(e);
        }
    }

}

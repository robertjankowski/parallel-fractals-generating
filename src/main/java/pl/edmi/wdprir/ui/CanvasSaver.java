package pl.edmi.wdprir.ui;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import javax.imageio.*;
import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CanvasSaver {

    private final static List<ExtensionFilter> extensionFilters = List.of(
            new ExtensionFilter("PNG files (*.png)", "*.png"),
            new ExtensionFilter("TIFF files (*.tiff)", "*.tiff"));
    private final static String DEFAULT_EXTENSION = "PNG";
    private static final double DPI = 600;
    private static final double INCH_2_CM = 2.54;

    public static void captureFractal(Stage stage, Canvas canvas) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save fractal image");
        fileChooser.getExtensionFilters().addAll(extensionFilters);
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try {
                String extension = getExtension(file, DEFAULT_EXTENSION);
                for (Iterator<ImageWriter> iw = ImageIO.getImageWritersByFormatName(extension); iw.hasNext(); ) {
                    ImageWriter writer = iw.next();
                    ImageWriteParam writeParam = writer.getDefaultWriteParam();
                    ImageTypeSpecifier typeSpecifier = ImageTypeSpecifier.createFromBufferedImageType(BufferedImage.TYPE_INT_RGB);
                    IIOMetadata metadata = writer.getDefaultImageMetadata(typeSpecifier, writeParam);
                    if (metadata.isReadOnly() || !metadata.isStandardMetadataFormatSupported()) {
                        continue;
                    }
                    setDPI(metadata);
                    try (ImageOutputStream stream = ImageIO.createImageOutputStream(file)) {
                        writer.setOutput(stream);
                        WritableImage image = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
                        canvas.snapshot(null, image);
                        RenderedImage renderedImage = SwingFXUtils.fromFXImage(image, null);
                        writer.write(metadata, new IIOImage(renderedImage, null, metadata), writeParam);
                    }
                    break;
                }
            } catch (IOException ex) {
                Logger.getLogger(CanvasSaver.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static String getExtension(File file, String defaultValue) {
        String fileName = file.getName();
        String[] fileWithExtension = fileName.split("\\.");
        if (fileWithExtension.length == 2) {
            return fileWithExtension[1].toUpperCase();
        } else {
            return defaultValue;
        }
    }

    private static void setDPI(IIOMetadata metadata) throws IIOInvalidTreeException {
        // From: https://stackoverflow.com/a/4833697/9511702
        double dotsPerMilli = 1.0 * DPI / 10 / INCH_2_CM;
        IIOMetadataNode horiz = new IIOMetadataNode("HorizontalPixelSize");
        horiz.setAttribute("value", Double.toString(dotsPerMilli));

        IIOMetadataNode vert = new IIOMetadataNode("VerticalPixelSize");
        vert.setAttribute("value", Double.toString(dotsPerMilli));

        IIOMetadataNode dim = new IIOMetadataNode("Dimension");
        dim.appendChild(horiz);
        dim.appendChild(vert);

        IIOMetadataNode root = new IIOMetadataNode("javax_imageio_1.0");
        root.appendChild(dim);
        metadata.mergeTree("javax_imageio_1.0", root);
    }
}

package com.example.Controllers;

import javafx.scene.layout.Region;
import javafx.scene.shape.SVGPath;

public class SVGHolder {
    private final static int iconWidth = 20;
    private final static int iconHeight = 15;

    public static Region createCloudGraphics() {
        SVGPath svg = new SVGPath();
        svg.setContent(
                "M 289.00,74.00 C 299.18,61.21 307.32,52.80 320.00,42.42 331.43,33.07 343.66,26.03 357.00,19.84 427.64,-12.98 509.92,2.91 564.42,58.28 583.93,78.10 595.94,99.15 605.58,125.00 607.76,130.86 611.37,144.75 612.54,151.00 613.15,154.23 613.28,160.06 615.58,162.44 617.49,164.42 624.11,165.84 627.00,166.86 634.80,169.62 639.97,172.04 647.00,176.42 673.69,193.07 692.76,221.39 695.83,253.00 700.60,302.03 676.64,345.41 630.00,364.00 621.17,367.52 608.48,370.99 599.00,371.00 599.00,371.00 106.00,371.00 106.00,371.00 96.50,370.99 87.00,368.97 78.00,366.00 36.29,352.22 6.21,312.25 6.00,268.00 5.77,219.90 34.76,179.34 81.00,165.02 96.78,160.14 107.02,161.00 123.00,161.00 124.59,150.68 130.49,137.79 136.05,129.00 150.70,105.88 173.22,88.99 200.00,82.65 213.13,79.55 219.79,79.85 233.00,80.00 247.37,80.17 264.61,85.94 277.00,93.00 279.11,86.37 284.67,79.45 289.00,74.00 Z");

        return appliquerPropriete(svg);
    }

    public static Region createUserCircle() {
        SVGPath svg = new SVGPath();

        svg.setContent(
                "M406.5 399.6C387.4 352.9 341.5 320 288 320H224c-53.5 0-99.4 32.9-118.5 79.6C69.9 362.2 48 311.7 48 256C48 141.1 141.1 48 256 48s208 93.1 208 208c0 55.7-21.9 106.2-57.5 143.6zm-40.1 32.7C334.4 452.4 296.6 464 256 464s-78.4-11.6-110.5-31.7c7.3-36.7 39.7-64.3 78.5-64.3h64c38.8 0 71.2 27.6 78.5 64.3zM256 512A256 256 0 1 0 256 0a256 256 0 1 0 0 512zm0-272a40 40 0 1 1 0-80 40 40 0 1 1 0 80zm-88-40a88 88 0 1 0 176 0 88 88 0 1 0 -176 0z");

        return appliquerPropriete(svg);
    }

    // TODO: Créer des autres méthodes pour spécifier la taille
    private static Region appliquerPropriete(SVGPath svg) {

        // Création de la région qui représente le SVG :
        Region svgShape = new Region();
        svgShape.setShape(svg);

        // Définition de la taille :
        svgShape.setMinSize(50,100);
        svgShape.setPrefSize(100, 150);
        svgShape.setMaxSize(150,200);

        // Changer la couleur :
        svgShape.setStyle("-fx-background-color: white;");

        return svgShape;
    }
}

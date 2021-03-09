import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import java.text.DecimalFormat;

public class Lab06 extends Application{

    private static double[] avgHousingPricesByYear = {
        247381.0, 264171.4, 287715.3, 294736.1, 308431.4, 322635.9, 340253.0, 363153.7
    };

    private static double[] avgCommercialPricesByYear = {
        1121585.3, 1219479.5, 1246354.2, 1295364.8, 1335932.6, 1472362.0, 1583521.9, 1613246.3
    };

    private static String[] ageGroups = {
        "18-25", "26-35", "36-45", "46-55", "56-65", "65+"
    };

    private static int[] purchasesByAgeGroup = {
        648, 1021, 2453, 3173, 1868, 2247
    };

    private static Color[] pieColours = {
        Color.AQUA, Color.GOLD, Color.DARKORANGE, Color.DARKSALMON, Color.LAWNGREEN, Color.PLUM
    };

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Lab06");

        Pane pane = new Pane();

        //////////////////////////////////////////////////////////////////////////
        // Bar Chart

        double maxHeight = 350.0;
        double maxWidth = 400.0 - avgHousingPricesByYear.length*5.0;

        double maxAHPPY = getMax(avgHousingPricesByYear);
        double maxACPPY = getMax(avgCommercialPricesByYear);

        double maxVal = (maxAHPPY > maxACPPY) ? maxAHPPY : maxACPPY;

        double[] ratiosAHPPY = new double[avgHousingPricesByYear.length];
        double[] ratiosACPPY = new double[avgCommercialPricesByYear.length];

        for (int i = 0; i < ratiosAHPPY.length; i++) {

            ratiosAHPPY[i] = avgHousingPricesByYear[i]/maxVal;
            ratiosACPPY[i] = avgCommercialPricesByYear[i]/maxVal;

        }

        Rectangle[] recAHPPY = new Rectangle[ratiosAHPPY.length];
        Rectangle[] recACPPY = new Rectangle[ratiosACPPY.length];
        Label[] labYears = new Label[ratiosACPPY.length];

        for (int i = 0; i < recAHPPY.length; i++) {
            recAHPPY[i] = new Rectangle(((maxWidth)/recAHPPY.length)/2, maxHeight*ratiosAHPPY[i], Color.RED);
            recACPPY[i] = new Rectangle(((maxWidth)/recACPPY.length)/2, maxHeight*ratiosACPPY[i], Color.BLUE);
            labYears[i] = new Label("Y" + i);
            labYears[i].setMaxWidth(((maxWidth)/recACPPY.length)/2);
            labYears[i].setAlignment(Pos.CENTER);

            recAHPPY[i].setX(((maxWidth)/recAHPPY.length) * i + 5 *i + 100);
            recACPPY[i].setX(((maxWidth)/recACPPY.length) * i + ((maxWidth)/recAHPPY.length)/2 + 5 *i + 100);
            labYears[i].setTranslateX(((maxWidth)/recACPPY.length) * i + ((maxWidth)/recAHPPY.length)/4 + 5 *i + 100);

            recAHPPY[i].setY(400-maxHeight*ratiosAHPPY[i]);
            recACPPY[i].setY(400-maxHeight*ratiosACPPY[i]);
            labYears[i].setTranslateY(410);

            pane.getChildren().add(recAHPPY[i]);
            pane.getChildren().add(recACPPY[i]);
            pane.getChildren().add(labYears[i]);
        }

        Label[] labPrices = new Label[11];
        DecimalFormat df = new DecimalFormat("0.0");
        for (int i = 0; i < 11; i++) {
            labPrices[i] = new Label(df.format(maxVal/10 * i));
            labPrices[i].setFont(new Font("Arial", 10));
            labPrices[i].setTranslateY(400-(350/10*i));
            labPrices[i].setTranslateX(50);
            pane.getChildren().add(labPrices[i]);
        }

        Label labXTitle = new Label("Years");
        Label labYTitle = new Label("Prices");
        Label labLegendAHPPY = new Label("Average Housing Prices");
        Label labLegendACPPY = new Label("Average Commercial Prices");
        Label labTitle = new Label("Housing vs. Commercial Prices");

        labXTitle.setMaxWidth(70);
        labXTitle.setAlignment(Pos.CENTER);
        labXTitle.setTranslateX(265);
        labXTitle.setTranslateY(425);

        labYTitle.setRotate(270);
        labYTitle.setAlignment(Pos.CENTER);
        labYTitle.setTranslateX(5);
        labYTitle.setTranslateY(400-175);

        labLegendAHPPY.setTranslateY(450);
        labLegendAHPPY.setTranslateX(30);
        labLegendAHPPY.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));

        labLegendACPPY.setTranslateY(470);
        labLegendACPPY.setTranslateX(30);
        labLegendACPPY.setBackground(new Background(new BackgroundFill(Color.BLUE, null, null)));

        labTitle.setFont(new Font("Arial", 30));
        labTitle.setTranslateX(20);

        //////////////////////////////////////////////////////////////////////////
        // Pie Chart

        int totalPurchases = 0;

        for (int i: purchasesByAgeGroup) {
            totalPurchases += i;
        }

        double[] anglesPBAG = new double[purchasesByAgeGroup.length];

        for (int i = 0; i < purchasesByAgeGroup.length; i++) {
            // We calculate the angle for each group
            anglesPBAG[i] = (double)(purchasesByAgeGroup[i])/totalPurchases * 360;
        }

        double startAngle = 0.0d;

        Arc[] arcs = new Arc[anglesPBAG.length];

        for (int i = 0; i < anglesPBAG.length; i++) {

            arcs[i] = new Arc(800, 300, 200, 200, startAngle, anglesPBAG[i]);
            arcs[i].setType(ArcType.ROUND);
            startAngle += anglesPBAG[i];
            arcs[i].setFill(pieColours[i]);

            pane.getChildren().add(arcs[i]);

        }


        //////////////////////////////////////////////////////////////////////////
        // Final javafx settings

        pane.getChildren().addAll(labXTitle, labYTitle, labLegendAHPPY, labLegendACPPY, labTitle);

        primaryStage.setScene(new Scene(pane, 1100, 600));

        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

    private static double getMax(double[] arr) {

        double max = arr[0];

        for (double candidate: arr) {
            if (candidate > max) {
                max = candidate;
            }
        }

        return max;

    }

}

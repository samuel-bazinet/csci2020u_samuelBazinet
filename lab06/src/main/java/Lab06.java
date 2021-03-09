import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.collections.*;
import javafx.scene.paint.Color;
import javafx.scene.chart.*;

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

        BorderPane border = new BorderPane();

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bC = new BarChart<>(xAxis, yAxis);
        
        bC.setTitle("Price of Housing vs. Commercial");
        xAxis.setLabel("Year");
        yAxis.setLabel("Price in $");

        XYChart.Series<String,Number> series1 = new XYChart.Series<>();
        series1.setName("Housing");
        for (int i = 0; i < avgHousingPricesByYear.length; i++) {
            series1.getData().add(new XYChart.Data<String,Number>("Y" + i, avgHousingPricesByYear[i]));
        }

        XYChart.Series<String,Number> series2 = new XYChart.Series<>();
        series2.setName("Commercial");
        for (int i = 0; i < avgCommercialPricesByYear.length; i++) {
            series2.getData().add(new XYChart.Data<String,Number>("Y" + i, avgCommercialPricesByYear[i]));
        }

        bC.getData().addAll(series1, series2);

        border.setLeft(bC);

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        for (int i = 0; i < ageGroups.length; i++) {
            pieChartData.add(new PieChart.Data(ageGroups[i], purchasesByAgeGroup[i]));
        }

        for (PieChart.Data data : pieChartData) {
            data.getNode().setStyle();
        }

        primaryStage.setScene(new Scene(border, 1000, 1000));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.canvas.*;
import javafx.stage.Stage;
import javafx.scene.Group;
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;;

public class Lab09  extends Application{

    private Canvas canvas;
    private int sceneWidth = 960;
    private int sceneHeight = 540;
    private static double[] dataSet1;
    private static double[] dataSet2;

    @Override
    public void start(Stage primaryStage) throws Exception {

        canvas = new Canvas();

        canvas.widthProperty().bind(primaryStage.widthProperty());
        canvas.heightProperty().bind(primaryStage.heightProperty());

        Group root = new Group();
        root.getChildren().add(canvas);
        

        Scene scene = new Scene(root, sceneWidth, sceneHeight);
        primaryStage.setScene(scene);
        primaryStage.show();

        drawLinePlot(root);
    } 

    public static void main(String[] args) {  
        
        downloadStockPrices("GOOG", "AAPL");

        launch(args);

    }

    private void drawLinePlot(Group root) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setStroke(Color.BLACK);

        gc.strokeLine(50, 50, 50, canvas.getHeight()-50);
        gc.strokeLine(50, canvas.getHeight()-50, canvas.getWidth()-50, canvas.getHeight()-50);

        plotLine(root);
    }

    private void plotLine(Group root) { 
        GraphicsContext gc = canvas.getGraphicsContext2D();

        double max1 = getMax(dataSet1);
        double max2 = getMax(dataSet2);
        double lineWidth = (canvas.getWidth()-100.0)/dataSet1.length;
        double totalHeight = (canvas.getHeight()-100.0);

        double absoluteMax = (max1>max2) ? max1 : max2;

        double[] adjustedDataSet1 = adjustDataSet(dataSet1, absoluteMax, totalHeight);
        double[] adjustedDataSet2 = adjustDataSet(dataSet2, absoluteMax, totalHeight);

        for (int i = 1; i < dataSet1.length; i++) {
            gc.setStroke(Color.RED);
            gc.strokeLine((i-1)*lineWidth + 50, canvas.getHeight()-(50 + adjustedDataSet1[i-1]), i*lineWidth+50, canvas.getHeight()-(50 + adjustedDataSet1[i]));
        }

        for (int i = 1; i < dataSet1.length; i++) {
            gc.setStroke(Color.BLUE);
            gc.strokeLine((i-1)*lineWidth + 50, canvas.getHeight()-(50 + adjustedDataSet2[i-1]), i*lineWidth + 50, canvas.getHeight()- (50 + adjustedDataSet2[i]));
        }
    }

    private static void downloadStockPrices(String stockTicker1, String stockTicker2) {
        try {
            InputStream inputStream = new URL("https://query1.finance.yahoo.com/v7/finance/download/" + stockTicker1 + "?period1=1293858000&period2=1609477200&interval=1mo&events=history&includeAdjustedClose=true").openStream();
            
            Files.copy(inputStream, Paths.get(System.getProperty("user.dir") + "/data1.csv"), StandardCopyOption.REPLACE_EXISTING);

            inputStream = new URL("https://query1.finance.yahoo.com/v7/finance/download/" + stockTicker2 + "?period1=1293858000&period2=1609477200&interval=1mo&events=history&includeAdjustedClose=true").openStream();
            
            Files.copy(inputStream, Paths.get(System.getProperty("user.dir") + "/data2.csv"), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        try {

            dataSet1 = new double[12];
            int count = 0;
            String row;
            BufferedReader br = new BufferedReader(new FileReader(new File(System.getProperty("user.dir") + "/data1.csv")));

            //The first line is not composed of double so we skip 
            row = br.readLine();

            while (null != (row = br.readLine())) {

                String cells[] = row.split(",");
                if (count == dataSet1.length) {
                    dataSet1 = extendArray(dataSet1);
                }

                dataSet1[count] = Double.parseDouble(cells[4]);
                count++;
            }

            dataSet2 = new double[12];
            count = 0;
            br = new BufferedReader(new FileReader(new File(System.getProperty("user.dir") + "/data2.csv")));

            //The first line is not composed of double so we skip 
            row = br.readLine();

            while (null != (row = br.readLine())) {
                
                String cells[] = row.split(",");
                if (count == dataSet2.length) {
                    dataSet2 = extendArray(dataSet2);
                }

                dataSet2[count] = Double.parseDouble(cells[4]);
                count++;
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    private static double[] extendArray(double[] arr) {
        double[] newArr = new double[arr.length + 1];
        for (int i = 0; i < arr.length; i++) {
            newArr[i] = arr[i];
        }
        return newArr;
    }

    private static double getMax(double[] arr) {
        double max = arr[0];
        for (double i: arr) {
            if (i > max) {
                max = i;
            }
        }
        return max;
    }

    private double[] adjustDataSet(double[] arr, double max, double height) {
        double[] adjustedDataSet = new double[arr.length];
        for (int i = 0; i < arr.length; i++) {
            adjustedDataSet[i] = (arr[i]/max)*height;
        }
        return adjustedDataSet;
    }
    
}

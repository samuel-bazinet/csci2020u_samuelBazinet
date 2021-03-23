import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import java.text.DecimalFormat;
import javafx.scene.input.MouseEvent;
import java.lang.Math;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Lab07 extends Application{

    private static Color[] pieColours = {
        Color.AQUA, Color.GOLD, Color.DARKORANGE, Color.DARKSALMON
    };

    @Override
    public void start(Stage primaryStage) {

        String row;

        Map<String, Integer> count = new TreeMap<>();

        //////////////////////////////////////////////////////////////////////////
        // File reading 

        try {

            BufferedReader br = new BufferedReader(new FileReader("weatherwarnings-2015.csv"));
            while(null != (row = br.readLine())) {
                String[] cells = row.split(",");
                String warning;
                switch(cells[5]) {
                    case "FLASH FLOOD":
                        warning = "FLASH FLOOD";
                        if (count.containsKey(warning)){
                            int previous = count.get(warning);
                            count.put(warning, previous+1);
                        } else {
                            count.put(warning, 1);
                        } 
                        break;
                    case "SEVERE THUNDERSTORM":
                        warning = "SEVERE THUNDERSTORM";
                        if (count.containsKey(warning)){
                            int previous = count.get(warning);
                            count.put(warning, previous+1);
                        } else {
                            count.put(warning, 1);
                        } 
                        break;
                    case "SPECIAL MARINE":
                        warning = "SPECIAL MARINE";
                        if (count.containsKey(warning)){
                            int previous = count.get(warning);
                            count.put(warning, previous+1);
                        } else {
                            count.put(warning, 1);
                        } 
                        break;
                    case "TORNADO":
                        warning = "TORNADO";
                        if (count.containsKey(warning)){
                            int previous = count.get(warning);
                            count.put(warning, previous+1);
                        } else {
                            count.put(warning, 1);
                        } 
                        break;
                    default:
                        System.out.println("Error: No match");
                }
            }

        } catch(IOException e) {
            e.printStackTrace();
        }

        //////////////////////////////////////////////////////////////////////////
        // Stats Calculating

        int totalWarnings = 0;
        Map<String, Double> ratios = new TreeMap<>();

        Set<String> keys = count.keySet();
        Iterator<String> keyIterator = keys.iterator();

        while (keyIterator.hasNext()) {
            String key = keyIterator.next();
            totalWarnings += count.get(key);
        }
        
        keyIterator = keys.iterator();

        while (keyIterator.hasNext()) {
            String key = keyIterator.next();
            ratios.put(key, (double)(count.get(key))/(double)(totalWarnings));
        }

        //////////////////////////////////////////////////////////////////////////
        // Pie Making

        Pane pane = new Pane();

        Arc[] arcs = new Arc[count.size()];

        double startAngle = 0.0d;

        int iter = 0;

        keys = ratios.keySet();
        keyIterator = keys.iterator();

        while (keyIterator.hasNext()) {
            String key = keyIterator.next();
            arcs[iter] = new Arc(500, 200, 150, 150, startAngle, (ratios.get(key)*360.0));
            arcs[iter].setType(ArcType.ROUND);
            arcs[iter].setFill(pieColours[iter]);
            startAngle += ratios.get(key)*360;

            pane.getChildren().add(arcs[iter]);
            iter++;
        }

        Label[] legend = new Label[count.size()];
        Rectangle[] rects = new Rectangle[count.size()];

        keyIterator = keys.iterator();
        keys = count.keySet();
        iter = 0;
        DecimalFormat df = new DecimalFormat("0.0");

        while(keyIterator.hasNext()) {
            String key = keyIterator.next();

            rects[iter] = new Rectangle(10, 20+25*iter, 50, 20);
            rects[iter].setFill(pieColours[iter]);

            legend[iter] = new Label(key + ": " + count.get(key).toString() + " : " + df.format(ratios.get(key)*100) + "%");
            legend[iter].setTranslateX(70);
            legend[iter].setTranslateY(20 + 25*iter);

            pane.getChildren().add(legend[iter]);
            pane.getChildren().add(rects[iter]);

            iter++;

        }


        primaryStage.setScene(new Scene(pane, 700, 400));

        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}

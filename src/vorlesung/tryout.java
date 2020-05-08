package vorlesung;

import java.util.Scanner;

public class tryout {
    public static void main(String[] args)
    {
        Boolean nichtGeratet = true;

        int zahl = 50, intwert ; double wert = 50;
        int total = 0;
        while(nichtGeratet){
            System.out.println("Ich habe diese nummer geratet  :" + zahl);
            Scanner eingabe = new Scanner(System.in);
            String unsereingabe = eingabe.nextLine();
            switch (unsereingabe) {
                case "R":
                    System.out.println("Ich bin froh");
                    nichtGeratet = false;
                    break;
                case "G":
                    System.out.println("Grosser dings gemacht");
                    total++;

                    wert = wert/2;
                    intwert = (int) Math.round(wert);
                    zahl = zahl + intwert;
                    break;
                case "K":
                    System.out.println("Kleiner dings gemacht");
                    wert = wert / 2;
                    intwert = (int) Math.round(wert);
                    zahl = zahl - intwert;
                    break;
            }
        }



    }
}
